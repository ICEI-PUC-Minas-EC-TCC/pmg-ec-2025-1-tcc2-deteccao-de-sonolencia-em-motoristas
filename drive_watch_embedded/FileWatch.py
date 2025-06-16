import mediapipe as mp
import cv2 as cv
from scipy.spatial import distance as dis
import threading
import datetime
import base64
import requests
import json
import time
import numpy as np
# Essa biblioteca foi comentada para testes realizados fora do Raspberry
#from picamera2 import Picamera2
import torch
import skfuzzy as fuzz
from skfuzzy import control as ctrl
import matplotlib.pyplot as plt
import pathlib

# Para testar
import os
import pandas as pd
from sklearn.metrics import classification_report, confusion_matrix, accuracy_score


ID_DEVICE = '001'

COLOR_WHITE = (255,255,255)
COLOR_RED = (0, 0, 255)  
COLOR_GREEN = (0, 255, 0)  

min_frameEyes = 9
min_frameMouth = 30

# original ratio tolerances here
#min_toleranceEyes = 4.8
#min_toleranceMouth = 1.4

# debug ratio tolerances for tests
min_toleranceEyes = 2
min_toleranceMouth = 1.24

last_capture_time = 0
capture_interval = 10  # Intervalo de 10 segundos entre os envios para o servidor
yolo_results = 0
frame_id = 0
yolo_model = 0
picam2 = 0
image = 0
capture = 0
vid_capture = 0
flagImg = False

# INICIO CONSTANTES E FLAGS PARA TESTES
DATASET_CLASSES = ['awake', 'sleepy', 'yawn', 'no_yawn']
DATASET_CLASSES_MINUS = ['awake', 'sleepy']
DATASET_DIR_FROM_ROOT = "kaggleset"
#DATASET_DIR_FROM_ROOT = "smallset"
DEBUG_RESULTS = "results.csv"
DEBUG_REPORT = "report.txt"
is_debug_use_path = True
YOLO_TO_LABEL = {
    'awake': 'awake',
    'sleeping': 'sleepy',
    'yawning': 'yawn'
}
HARDCODED_LOOP_LIMIT = 15000
ALLOW_DEBUG_SAVE_FOLDER = False
ALLOW_PRINT_LAST_ARRAY = False
# Inicializa globais de teste
y_true_kg = []
y_pred_kg = []
logs_kg = []
# FIM CONSTANTES E FLAGS PARA TESTES


# Inicializa contadores e flag
frame_count = 0
frame_countM = 0
flagBocejo = 0

face_mesh = mp.solutions.face_mesh
draw_utils = mp.solutions.drawing_utils
landmark_style = draw_utils.DrawingSpec((0,255,0), thickness=1, circle_radius=1)
connection_style = draw_utils.DrawingSpec((0,0,255), thickness=1, circle_radius=1)

# Pontos de referência para a linha superior e inferior do olho esquerdo
LEFT_EYE_TOP_BOTTOM = [386, 374]
LEFT_EYE_LEFT_RIGHT = [263, 362]

# Pontos de referência para a linha superior e inferior do olho direito
RIGHT_EYE_TOP_BOTTOM = [159, 145]
RIGHT_EYE_LEFT_RIGHT = [133, 33]

# Pontos de referência para a linha superior e inferior dos lábios
UPPER_LOWER_LIPS = [13, 14]
LEFT_RIGHT_LIPS = [78, 308]


def load_yolo(OS):
    global yolo_model
    try:
        if OS == '1':
            yolo_model = torch.hub.load('ultralytics/yolov5', 'custom', path='/home/hmoraesc/DriveWatch/runs/train/exp3/weights/best.pt', force_reload = False)

        elif OS == '2':
            # Substitui temporariamente PosixPath por WindowsPath
            temp = pathlib.PosixPath
            pathlib.PosixPath = pathlib.WindowsPath
            
            yolo_model = torch.hub.load('ultralytics/yolov5', 'custom', path='runs/train/exp3/weights/last.pt', force_reload = False)

            # Restaura PosixPath para evitar problemas futuros
            pathlib.PosixPath = temp
            
    except Exception as e:
        print(f"Erro ao carregar o modelo YOLO: {e}")
        yolo_model = None


# Lembre de rodar pip install seaborn ou isso vai falhar
def process_yolo(image):
    """Processa a imagem usando o modelo YOLO e retorna as probabilidades de cada classe"""
    if yolo_model is None:
        return {'awake': 100, 'sleeping': 0, 'yawning': 0}
        
    results = yolo_model(image)
    
    # Valores padrão caso não haja detecção
    # Edit 07-06: Nao entendi o awake ser 100 e estava dando errado quando o YOLO deveria responder
    #class_probs = {'awake': 100, 'sleeping': 0, 'yawning': 0}
    class_probs = {'awake': 0, 'sleeping': 0, 'yawning': 0}
    
    
    # Extrai as classes e probabilidades das detecções
    
    if results is None or len(results.xyxy[0]) == 0:
        print("Nenhuma detecção encontrada no YOLO.")
        return class_probs  # Retorna valores padrão se não houver detecção
        
    if len(results.xyxy[0]) > 0:
        # Organiza as detecções por confiança (do maior para o menor)
        detections = results.xyxy[0].cpu().numpy()
        
        # Para cada detecção, obtém a classe e a confiança
        for detection in detections:
            confidence = detection[4] * 100  # Converte para porcentagem
            class_idx = int(detection[5])
            
            # Mapeia o índice da classe para o nome
            class_names = {1: 'awake', 0: 'sleeping', 2: 'yawning'}
            if class_idx in class_names:
                class_name = class_names[class_idx]
                class_probs[class_name] = confidence

                yolo_results = class_probs
                
                # Informações do YOLO
                if yolo_model is not None:
                    #cv.putText(image, f"YOLO Dormindo: {yolo_results['sleeping']:.1f}%", (10, 120), cv.FONT_HERSHEY_SIMPLEX, 0.7, COLOR_WHITE, 2)
                    #cv.putText(image, f"YOLO Bocejando: {yolo_results['yawning']:.1f}%", (10, 150), cv.FONT_HERSHEY_SIMPLEX, 0.7, COLOR_WHITE, 2)
                                
                    print(f"YOLO Dormindo: {yolo_results['sleeping']:.1f}%")
                    print(f"YOLO Bocejando: {yolo_results['yawning']:.1f}%")
                                
                    return yolo_results
    
    
def draw_landmarks(image, outputs, land_mark, color):
    height, width = image.shape[:2]
    for face in land_mark:
		# Itera sobre os pontos de referência do rosto
        point = outputs.multi_face_landmarks[0].landmark[face]
        
		#Escala as coordenadas normalizadas para as dimensões da imagem
        point_scale = (int(point.x * width), int(point.y * height))
        
        # Desenha um círculo no ponto de referência
        cv.circle(image, point_scale, 2, color, 1)

def euclidean_distance(image, top, bottom):
    height, width = image.shape[0:2]
    
    point1 = (int(top.x * width), int(top.y * height))
    point2 = (int(bottom.x * width), int(bottom.y * height))
    
    # Calcula a distância euclidiana entre dois pontos
    distance = dis.euclidean(point1, point2)
    return distance


def get_aspect_ratio(image, outputs, top_bottom, left_right):
    landmark = outputs.multi_face_landmarks[0]
    
    top = landmark.landmark[top_bottom[0]]
    bottom = landmark.landmark[top_bottom[1]]
    
    # Calcula a distância entre os pontos superior e inferior
    top_bottom_dis = euclidean_distance(image, top, bottom)
    
    left = landmark.landmark[left_right[0]]
    right = landmark.landmark[left_right[1]]
    
    # Calcula a distância entre os pontos esquerdo e direito
    left_right_dis = euclidean_distance(image, left, right)

    # NEW impede divisao por zero
    if top_bottom_dis == 0:
        print("[AVISO] Divisao por zero encontrada em get_aspect_ratio! Retornando valor alto")
        return min_toleranceMouth+1
    
    # Calcula a relação de aspecto entre as distâncias esquerda-direita e superior-inferior
    aspect_ratio = left_right_dis / top_bottom_dis
    
    return aspect_ratio


def post_image(id_device, image_path):
    image = cv.imread(image_path)
    max_width = 300
    max_height = 300
    if image is None:
        print(f"Failed to load image: {image_path}")
    height, width = image.shape[:2]
    if width > max_width or height > max_height:
        scaling_factor = min(max_width / width, max_height / height)
        new_size = (int(width * scaling_factor), int(height * scaling_factor))
        resized_image = cv.resize(image, new_size, interpolation=cv.INTER_AREA)
        cv.imwrite(image_path, resized_image)
        
    with open(image_path, "rb") as image_file:
		# Converte a imagem para base64
        encoded_string = base64.b64encode(image_file.read()).decode('utf-8')
        
        #print(encoded_string)
        
    data = {
        "idDevice": id_device,
        "image": encoded_string,
        "type": "SLEEPING",
        "occurrenceDate": datetime.datetime.now().isoformat()
    }
    
    url = "https://drivewatchbackend-production.up.railway.app/api/v1/register"
    headers = {'Content-Type': 'application/json'}
    response = requests.post(url, data=json.dumps(data), headers=headers)
    
    print(f"Status Code: {response.status_code}")
    print(f"Response: {response.json()}")
    

def init_camera(origem):
    if origem == '1':
        # Inicializa a picamera2
        global picam2
        picam2 = Picamera2()
        picam2.preview_configuration.main.size = (640, 480)  # Ajuste a resolução conforme necessário
        picam2.preview_configuration.main.format = "RGB888"
        picam2.configure("preview")
        picam2.start()
    
    elif origem == '2':
        global capture
        capture = cv.VideoCapture(0) #Em caso de erro mude esse valor
        
    elif origem == '3':
        global image
        if is_debug_use_path == True:
            image = None
        else:
            image = cv.imread('/home/hmoraesc/DriveWatch/001_17-04-2025_02-30-03.jpg')
        
    else:
        global vid_capture
        vid_capture = cv.VideoCapture('/home/hmoraesc/DriveWatch/v3.mp4')
        global min_toleranceEyes
        min_toleranceEyes = 3.7

def pause_and_debug(image, fpath, label, resposta_ob, aggMetrics):
    #ESC_KEY = 27
    #print(f"(DD) Iterando por {fpath} | Label: {label} | Resp: {resposta_ob} | Metrics: {aggMetrics} | Pressione esc pra continuar")
    print("\n[DEBUG] Iteração de análise:")
    print(f" - Caminho do arquivo: {fpath}")
    print(f" - Rótulo (label): {label}")
    print(f" - Resposta do modelo: {resposta_ob}")
    print(f" - Métricas agregadas: {aggMetrics}")
    print("==========================================")

    # esperar pra continuar
    #user_decision = input("Pressione ENTER para continuar ou digite 'exit' para encerrar: ").strip().lower()

    # deixar assim caso nao esteja realizando DEBUG de testes com imagens. Sim eu sei que isso esta pessimo, me perdoem.
    user_decision = "skip"
    
    if user_decision == "exit":
        print("[DEBUG] Execução encerrada pelo usuário.")
        exit(0)  # ou return, se você quiser apenas sair da iteração
    elif user_decision == "store":
        cv.imwrite(f"debug_stored.jpg", image)
        return 0

# Guarda imagens que falharem a comparacao de confianca
def store_and_debug(image, fpath):
    if not ALLOW_DEBUG_SAVE_FOLDER:
        return
    
    # Extrai o nome do arquivo com extensão, ex: "imagem1.png"
    filename = os.path.basename(fpath)

    # Garante que a pasta "debug_errored" exista
    debug_dir = os.path.join(os.getcwd(), "debug_errored")
    os.makedirs(debug_dir, exist_ok=True)

    # Caminho completo para salvar a imagem
    save_path = os.path.join(debug_dir, filename)
        
    # Salva a imagem
    success = cv.imwrite(save_path, image)
    if success:
        print(f"[DEBUG] Imagem salva em: {save_path}")
    else:
        print("[ERRO] Falha ao salvar a imagem.")

# function determine_final
#def determine_final(img, label, ear, mar, yolo_result):
    #do nothing for now

# fim function determine_final
            
# Uma tentativa de modularizar o laco While
# esse metodo faz exatamente o que esta no While true depois dos if else
# utilizado nos testes com arquivos externos
def iterate_through_frame(image, fpath, label):
    # Pulls de globals que serao modificadas
    global flagBocejo
    global frame_count
    global frame_countM

    # Defines adicionais para evitar exceptions
    yolo_conf = -1
    
    # Aviso de debug rodando
    print("[DEBUG] Iniciando mais uma iteracao de frame...")
    
    # Converte o frame para o espaço de cores RGB
    image_rgb = cv.cvtColor(image, cv.COLOR_BGR2RGB)
    
    # Cria uma cópia da imagem
    clean_image = image.copy()
    
    frame_h, frame_w, _ = image.shape
    
    image_rgb.flags.writeable = False
    
    outputs = face_model.process(image_rgb)
    
    image_rgb.flags.writeable = True
    
    image_rgb = cv.cvtColor(image_rgb, cv.COLOR_RGB2BGR)

    img_h, img_w, img_c = image_rgb.shape
    face_2d = []
    face_3d = []
    
    DROWSY_TIME_txt_pos = (10, int(frame_h // 2 * 1.7))
    
    # Processa o frame para detectar rostos
    outputs = face_model.process(image)
    if outputs.multi_face_landmarks:  
        for face_landmarks in outputs.multi_face_landmarks:
            for idx, lm in enumerate(face_landmarks.landmark):
                if idx in [33, 263, 1, 61, 291, 199]:
                    if idx == 1:
                        nose_2d = (lm.x * img_w, lm.y * img_h)
                        nose_3d = (lm.x * img_w, lm.y * img_h, lm.z * 3000)
                    x, y = int(lm.x * img_w), int(lm.y * img_h)
                    face_2d.append([x, y])
                    face_3d.append([x, y, lm.z])
    
    
            face_2d = np.array(face_2d, dtype=np.float64)
            face_3d = np.array(face_3d, dtype=np.float64)
            focal_length = 1 * img_w
            cam_matrix = np.array([[focal_length, 0, img_h/2],
                                   [0, focal_length, img_w/2],
                                   [0, 0, 1]])
            distortion_matrix = np.zeros((4, 1), dtype=np.float64)
    
            result, rotation_vec, translation_vec = cv.solvePnP(face_3d, face_2d, cam_matrix, distortion_matrix)
            
            #getting rotational of face
            rmat, jac = cv.Rodrigues(rotation_vec)
            angles, mtxR, mtxQ, Qx, Qy, Qz = cv.RQDecomp3x3(rmat)
            x_angle = angles[0] * 360
            y_angle = angles[1] * 360
            z_angle = angles[2] * 360
            text = ""
            if x_angle < -18:
                text = "Pare o veiculo"
            elif x_angle < -11.5:
                text = "Sinal de sonolencia"
    
            nose_3d_projection, jacobian = cv.projectPoints(nose_3d, rotation_vec, translation_vec, cam_matrix, distortion_matrix)
            
            p1 = (int(nose_2d[0]), int(nose_2d[1]))
            p2 = (int(nose_2d[0] + y_angle * 10), int(nose_2d[1] - x_angle * 10))
            
            cv.line(image, p1, p2, (255, 0, 0), 3)
    
            cv.putText(image, text, (20, 50), cv.FONT_HERSHEY_SIMPLEX, 2, (0, 255, 0), 2)
        # Desenha e calcula a relação de aspecto para os olhos e lábios
        draw_landmarks(image, outputs, LEFT_EYE_TOP_BOTTOM, COLOR_GREEN)
        draw_landmarks(image, outputs, LEFT_EYE_LEFT_RIGHT, COLOR_WHITE)
        ratio_left = get_aspect_ratio(image, outputs, LEFT_EYE_TOP_BOTTOM, LEFT_EYE_LEFT_RIGHT)
    
	    # Desenha os pontos de referência do olho direito
        draw_landmarks(image, outputs, RIGHT_EYE_TOP_BOTTOM, COLOR_GREEN)
        draw_landmarks(image, outputs, RIGHT_EYE_LEFT_RIGHT, COLOR_WHITE)
        ratio_right = get_aspect_ratio(image, outputs, RIGHT_EYE_TOP_BOTTOM, RIGHT_EYE_LEFT_RIGHT)
    
	    # Desenha os pontos de referência dos labios
        draw_landmarks(image, outputs, UPPER_LOWER_LIPS, COLOR_GREEN)
        draw_landmarks(image, outputs, LEFT_RIGHT_LIPS, COLOR_WHITE)

	    # Calcula a média da relação de aspecto entre os olhos esquerdo e direito (EAR)
        ratio = (ratio_left + ratio_right) / 2.0
        cv.putText(image, "EAR: {:.2f}".format(ratio), (480, 30), cv.FONT_HERSHEY_SIMPLEX, 0.7, (0, 0, 255), 2)

        # Calcula se a boca está aberta (MAR)
        ratio_lips = get_aspect_ratio(image, outputs, UPPER_LOWER_LIPS, LEFT_RIGHT_LIPS)
        cv.putText(image, "MAR: {:.2f}".format(ratio_lips), (480, 60), cv.FONT_HERSHEY_SIMPLEX, 0.7, (0, 0, 255), 2)

        # Se EAR maior que a tolerancia acionar yolo abaixo
        if ratio > min_toleranceEyes:
            # Se a origem for imagem estatica, isso pula a validacao de frames
            # caso contrario soma 1 na contagem de frames
            if origem == '3':
                frame_count = min_frameEyes + 1
            else:
                frame_count += 1
            # Necessario para indicar que os olhos estao de fato fechados caso seja video
            if frame_count > 2:
                cv.putText(image, "Eyes Closed", (10, 30), cv.FONT_HERSHEY_SIMPLEX, 0.7, (0, 0, 255), 2)
        else:
            # Se EAR menor que a tolerancia e origem estatica, resetar contador
            if origem == '3':
                frame_count = 0
            # Caso contrario, precisamos zerar? Nao faria sentido se o loop continua, apenas apos deteccao, pela logica
            #frame_count = 0
        # fim bloco if-else do EAR
    
        current_time = time.time()
        global last_capture_time

        # No caso de imagens estaticas o tempo de captura eh irrelevante entao considerar 20 min a mais
        if origem == '3':
            current_time = last_capture_time + 1200

        # inicio if MAR
        # Trata razao dos labios setando um flagBocejo, importante pra evitar looping eterno
        if ratio_lips < min_toleranceMouth:
            # Se a origem for imagem estática, isso pula a validacao de frames
            if origem == '3':
                frame_countM = min_frameMouth + 1
            else:
                frame_countM += 1
            # Boca aberta
            cv.putText(image, "Boca aberta", (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.7, (0, 0, 255), 2)
            flagBocejo = 1
            print(f"[DEBUG] Flag bocejo acionada! MAR de {ratio_lips} foi menor que {min_toleranceMouth}")
        else:
            # Se MAR acima da tolerancia minima, entao desconsiderar?
            if origem == '3':
                frame_countM = 0
            # o codigo anterior zerava o frame aqui independente da origem
            #frame_countM = 0
            # a flag deve ser zerada para processar
            flagBocejo = 0
            print("[DEBUG] Flag bocejo ZERADA > achamos que nao eh um bocejo com base no MAR!")
        # fim bloco if-else MAR

        # debug only please ignore
        print(f"[DEBUG] MAR obtida = {ratio_lips} tolerancia = {min_toleranceMouth} | Frames alterados para {frame_countM}")
        
        # Se estivermos dando entrada com imagens
        # A contagem de frames eh irrelevante (sempre sera um)
        #if origem == '3':
        #    frame_count = min_frameEyes + 1
        # Edit 28-05: Nao realizo o mesmo aqui, pois se nao loop eterno pre-deteccao
        
        # YOLO ou nao rodar o YOLO depende da deteccao dos frames (se for maior que o limiar)
        # Edit 28-05: O codigo abaixo consideraria ambos frames e 1 YOLO rodado
        if frame_count > min_frameEyes or frame_countM > min_frameMouth:
            #yolo_result = process_yolo(clean_image)
            #yolo_class = max(yolo_result, key=yolo_result.get)
            #yolo_conf = yolo_result[yolo_class]
            #print(yolo_result)
            #exit()
            # valor padrao de resposta preditiva
            #pred_res = 'none'
            print("[DEBUG] Sonolencia Detectada! Razao: EAR ou MAR")
            print(f"[DEBUG FRAMES] frame_count = {frame_count} tol = {min_frameEyes} | frame_count_mouth = {frame_countM} tol = {min_frameMouth}")
        else:
            #Sonolencia nao detectada (pular YOLO)
            yolo_class = 'awake'
            yolo_conf = 0
            pred_res = 'awake'
            print(f"\nNAO DETECTADA SONOLENCIA!\n frame_count = {frame_count} tol = {min_frameEyes} | frame_count_mouth = {frame_countM} tol = {min_frameMouth}")
            # Edit 04-06: Resolvi tentar rodar o YOLO mesmo se falhar, especialmente quando falhar!
            yolo_result = process_yolo(clean_image)
            yolo_class = max(yolo_result, key=yolo_result.get)
            yolo_conf = yolo_result[yolo_class]
            print(f"[YOLO RE-RUN] Rodei denovo! Resultados: {yolo_class} com confianca {yolo_conf}")
            print(yolo_results)
        # fim if-else YOLO or not YOLO

        # Indicacao que existe fadiga no tempo fornecido. Espelho do if original
        # leva em conta: frames de olhos fechados, tempo de captura e flagBocejo
        if frame_count > min_frameEyes and (current_time - last_capture_time) > capture_interval and flagBocejo == 0:
            # frames de  deteccao, isso so importa em caso de video aparentemente
            yolo_result = process_yolo(clean_image)
            yolo_class = max(yolo_result, key=yolo_result.get)
            yolo_conf = yolo_result[yolo_class]
            #if process_yolo(clean_image)['sleeping'] >= 50:
            if yolo_result['sleeping'] >= 50:
                # Dormindo
                cv.putText(image, "DROWSINESS ALERT!", (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.7, (0, 0, 255), 2)
                
                # Salva a imagem capturada
                # Comentado pois nesse modulo nosso interesse esta apenas nas metricas
                # Isso daqui duplicaria cada entrada do dataset, o que nao eh ideal
                #image_path = f'{ID_DEVICE}_{datetime.datetime.now().strftime("%d-%m-%Y_%H-%M-%S")}.jpg'
                #cv.imwrite(image_path, clean_image)
                
                # Atualiza o tempo da última captura
                last_capture_time = current_time

                # Comentado pois nesse modulo nosso interesse esta apenas nas metricas
                # Isso daqui faria um POST para cada entrada do dataset, o que nao eh ideal
                #threading.Thread(target=post_image, args=(ID_DEVICE, image_path)).start()

                # Metricas
                #pred_res = pred_res + ' sleepy'
                pred_res = 'sleepy'
                # LOGGING acontece depois, para termos o resultado total
                #log_entry = f"{label} - EAR: {ratio:.2f}, MAR: {mar:.2f}, YOLO: {yolo_class} ({yolo_conf:.1f}%) => {pred_metric}"
                print("Dormindo")
            #fim if yolo_result
            else:
                print("EAR indicou dormindo, mas YOLO nao concordou!")
                print(f"YOLO resolveu para {yolo_class} com {yolo_conf} de confianca!")
                if origem == '3':
                    # Temporariamente atribuir o oposto (evitar exception). Caso faca sentido re-interpretamos no if temp
                    pred_res = 'awake'
                    temp_pred_res = YOLO_TO_LABEL.get(yolo_class, "unknown")
                    # Evita classificacao falha quando MAR falha mas YOLO acerta
                    if temp_pred_res == 'yawn':
                        pred_res = 'sleepy'
                    print("[DEBUG-3] CONFIANCA BAIXA!! Confianca menor que 50 para dormindo, obtida pred_res interpretada = " + pred_res)
            #fim else do if yolo_result

            if origem == '3':
                # Nesse caso queremos registrar mesmo se a confianca for menor que 50
                #pred_res = YOLO_TO_LABEL.get(yolo_class, "unknown")
                print(f"[DEBUG-3] YOLO RODADO COM CONFIANCA {yolo_conf}")
                print(yolo_result)
                #print("[DEBUG-3] Confianca menor que 50 para dormindo, obtida pred_res exclusiva = " + pred_res)
        #fim if  frame

        # Os calculos da MAR ja foram feitos acima, para permitir logging
        
        if frame_countM > min_frameMouth:
            # YOLOeehiiu
            yolo_result = process_yolo(clean_image)
            yolo_class = max(yolo_result, key=yolo_result.get)
            yolo_conf = yolo_result[yolo_class]
            if yolo_result['yawning'] >= 50:
                # Bocejando
                cv.putText(image, "Bocejo", (10, 80), cv.FONT_HERSHEY_SIMPLEX, 0.7, (0, 0, 255), 2)
                #pred_res = pred_res + ' yawning'
                # Mudei pra sobrescrever, agora fadiga com bocejo sera classificado como sleepy
                pred_res = 'sleepy'
                
                print("Bocejando")
            else:
                # Nesse caso queremos registrar mesmo se a confianca for menor que 50
                if origem == '3':
                    pred_res = YOLO_TO_LABEL.get(yolo_class, "unknown")
                    print("[DEBUG-3] Confianca para bocejo abaixo de 50, Obtida pred_res exclusiva = " + pred_res)
            if origem == '3':
                #pred_res = YOLO_TO_LABEL.get(yolo_class, "unknown")
                print(f"[DEBUG-3] YOLO RODADO COM CONFIANCA {yolo_conf}")
                print(yolo_result)
                #print("[DEBUG-3] Confianca para bocejo abaixo de 50, Obtida pred_res exclusiva = " + pred_res)
        #fim if MAR > tolerance

        # [LOGGING] INICIO DO LOG
        # Esquema: (predicao_esperada) - EAR, MAR, YOLO => resolucao
        if yolo_conf > 0:
            log_entry = f"TARGET: {label} - EAR: {ratio:.2f}, MAR: {ratio_lips:.2f}, YOLO: {yolo_class} ({yolo_conf:.1f}%) => {pred_res}"
        else:
            log_entry = f"TARGET: {label} - EAR: {ratio:.2f}, MAR: {ratio_lips:.2f}, YOLO: nao utilizado => {pred_res}"
        logs_kg.append(f"{fpath} - {log_entry}")
        #print(logs_kg)
        # [LOGGING] FIM DO LOG
        # [PREDICTION] INICIO
        if pred_res is not None:
            y_true_kg.append(label)
            y_pred_kg.append(pred_res)
            # DEBUG only
            print(f"Final da Iteracao: ESPERADO = {label} | OBTIDO = {pred_res}")
            print(f"Adicionais da iteracao: EAR = {ratio} | MAR = {ratio_lips}")
            if pred_res != label:
                tti = f"E:{label}|O:{pred_res}"
                cv.putText(image, tti, (10, 80), cv.FONT_HERSHEY_SIMPLEX, 0.7, (0, 0, 255), 2)
                store_and_debug(image,fpath)
            # end of DEBUG only
    #end of if outputs.multifacelandmarks
    else:
        print("[ERRO] Nao conseguimos identificar um rosto! Iremos INFERIR awake para fins de analise!")
        #pred_res = "no_face"
        pred_res = "awake"
        logs_kg.append(f"{fpath} - TARGET: {label} - No face landmarks detected! Inferred awake")
        y_true_kg.append(label)
        y_pred_kg.append(pred_res)
        store_and_debug(image, fpath)
    #end of else from if outputs
    pause_and_debug(image, fpath, label, pred_res, "none")

    # Comentei pois nao queremos mostrar imagens na fase de testes
    #cv.imshow("Drive Watch", image)
    
    # Ignorado porque queremos que isso rode todas imagens do dataset
    # Pressione 'ESC' para sair
    #if cv.waitKey(1) & 0xFF == 27:
    #    break
# fim metodo iterate_through_frame

        
# Inicializa o modelo de detecção de rosto
face_model = face_mesh.FaceMesh(static_image_mode = False,
                                max_num_faces = 1,
                                min_detection_confidence = 0.5,
                                min_tracking_confidence = 0.5)



print("Selecione o sistema operacional:")
print("1 - Linux")
print("2 - Windows")
OS = input()

if OS not in ['1', '2']:
    print("Opção inválida!")
    exit()
    
load_yolo(OS)

print("\nSelecione a origem da imagem:")
print("1 - PiCamera")
print("2 - Câmera USB")
print("3 - Caminho de imagem")
print("4 - Caminho de video")
origem = input()

if origem not in ['1', '2', '3', '4']:
    print("Opção inválida!")
    exit()
    
init_camera(origem)


# Cada diretorio se refere ao resultado esperado do label
# Inicializar opcoes de teste a partir de arquivo
if is_debug_use_path == True:
    print("\n[INFO] Iniciando testes atraves de arquivos... lendo labels...")
    loop_itera = 1
    # O label pertence a alguma das pastas [awake, no_yawn, sleepy, yawn]
    # mas para o escopo da decisao sonolencia ou nao, unificamos awake com no_yawn e sleepy com yawn
    # portanto a variavel 'used_label' se refere a essa 'traducao' entre labels
    for label in DATASET_CLASSES:
        class_dir = os.path.join(DATASET_DIR_FROM_ROOT, label)
        for fname in os.listdir(class_dir):
            if not fname.lower().endswith(('.jpg', '.png')):
                continue
            fpath = os.path.join(class_dir, fname)
            image = cv.imread(fpath)
            if image is None:
                logs.append(f"{fpath} - erro ao carregar")
                continue
            # A partir daqui esta tudo validado e segue o fluxo padrao
            if label == 'no_yawn':
                used_label = 'awake'
            elif label == 'yawn':
                used_label = 'sleepy'
            else:
                used_label = label
            # DEBUG: saber qual item estiver sendo utilizado
            print(f"Iniciando iteracao por label {label} em {fpath}")
            # Roda metodo de iteracao frame to frame
            iterate_through_frame(image, fpath, used_label)
            #cleaner_iterattron(image, fpath, label)
            # Contador de iteracoes para controle adicional
            loop_itera += 1
            if loop_itera == HARDCODED_LOOP_LIMIT:
                print("[FATAL ERROR] Atingiu o limite hardcoded de loops, algo de errado aconteceu!")
                exit()
        # fim bloco for fname
    # fim bloco for label
    # Exporta resultados
    pd.DataFrame({'true': y_true_kg, 'predicted': y_pred_kg}).to_csv(DEBUG_RESULTS, index=False)
    with open(DEBUG_REPORT, 'w') as f:
        acc = accuracy_score(y_true_kg, y_pred_kg)
        f.write(f"Accuracy: {acc:.4f}\n\n")
        if ALLOW_PRINT_LAST_ARRAY:
            print(y_pred_kg)
        f.write("=== Classification Report ===\n")
        f.write(classification_report(y_true_kg, y_pred_kg, target_names=DATASET_CLASSES_MINUS))
        #f.write("\n=== Confusion Matrix ===\n")
        #cm = confusion_matrix(y_true_kg, y_pred_kg, labels=DATASET_CLASSES_MINUS)
        #f.write(pd.DataFrame(cm, index=DATASET_CLASSES_MINUS, columns=DATASET_CLASSES_MINUS).to_string())
    print("\n[INFO] Avaliação completa finalizada.")
    print(f"[INFO - COMPLETED] Resultados salvos em {DEBUG_RESULTS} e {DEBUG_REPORT}")
    exit()
# fim bloco if



# BLOCO WHILE TRUE PRINCIPAL ABAIXO

while True and flagImg == False:
    if origem == '1':
        # Captura um frame da picamera
        image = picam2.capture_array()
        
        if image is None:
            continue  # Se não capturar o frame, pula para a próxima iteração
            
    elif origem == '2':
        if capture.isOpened():
            result, image = capture.read()
            if image is None:
                continue  # Se não capturar o frame, pula para a próxima iteração
                
    elif origem == '3':
        flagImg = True
        if is_debug_use_path == True:
            print("Flag is_debug_use_path utilizada, saindo do programa...")
            exit()
        if image is None:
            print("Erro: Não foi possível carregar a imagem.")
            break
    
    else:
        if vid_capture.isOpened():
            result, image = vid_capture.read()
            if image is None:
                continue  # Se não capturar o frame, pula para a próxima iteração
        

    # Converte o frame para o espaço de cores RGB
    image_rgb = cv.cvtColor(image, cv.COLOR_BGR2RGB)
    
    # Cria uma cópia da imagem
    clean_image = image.copy()
    
    frame_h, frame_w, _ = image.shape
    
    image_rgb.flags.writeable = False
    
    outputs = face_model.process(image_rgb)
    
    image_rgb.flags.writeable = True
    
    image_rgb = cv.cvtColor(image_rgb, cv.COLOR_RGB2BGR)

    img_h, img_w, img_c = image_rgb.shape
    face_2d = []
    face_3d = []
    
    DROWSY_TIME_txt_pos = (10, int(frame_h // 2 * 1.7))
    
    # Processa o frame para detectar rostos
    outputs = face_model.process(image)
        
    
    if outputs.multi_face_landmarks:  
        for face_landmarks in outputs.multi_face_landmarks:
            for idx, lm in enumerate(face_landmarks.landmark):
                if idx in [33, 263, 1, 61, 291, 199]:
                    if idx == 1:
                        nose_2d = (lm.x * img_w, lm.y * img_h)
                        nose_3d = (lm.x * img_w, lm.y * img_h, lm.z * 3000)
                    x, y = int(lm.x * img_w), int(lm.y * img_h)
                    face_2d.append([x, y])
                    face_3d.append([x, y, lm.z])
    
    
            face_2d = np.array(face_2d, dtype=np.float64)
            face_3d = np.array(face_3d, dtype=np.float64)
            focal_length = 1 * img_w
            cam_matrix = np.array([[focal_length, 0, img_h/2],
                                   [0, focal_length, img_w/2],
                                   [0, 0, 1]])
            distortion_matrix = np.zeros((4, 1), dtype=np.float64)
    
            result, rotation_vec, translation_vec = cv.solvePnP(face_3d, face_2d, cam_matrix, distortion_matrix)
            
            #getting rotational of face
            rmat, jac = cv.Rodrigues(rotation_vec)
            angles, mtxR, mtxQ, Qx, Qy, Qz = cv.RQDecomp3x3(rmat)
            x_angle = angles[0] * 360
            y_angle = angles[1] * 360
            z_angle = angles[2] * 360
    
            text = ""
            if x_angle < -18:
                text = "Pare o veiculo"
            elif x_angle < -11.5:
                text = "Sinal de sonolencia"
    
            nose_3d_projection, jacobian = cv.projectPoints(nose_3d, rotation_vec, translation_vec, cam_matrix, distortion_matrix)
            
            p1 = (int(nose_2d[0]), int(nose_2d[1]))
            p2 = (int(nose_2d[0] + y_angle * 10), int(nose_2d[1] - x_angle * 10))
            
            cv.line(image, p1, p2, (255, 0, 0), 3)
    
            cv.putText(image, text, (20, 50), cv.FONT_HERSHEY_SIMPLEX, 2, (0, 255, 0), 2)
            #cv.putText(image, "x: " + str(np.round(x_angle, 2)), (500, 50), cv.FONT_HERSHEY_SIMPLEX, 1, (0, 0, 255), 2)
            #cv.putText(image, "y: " + str(np.round(y_angle, 2)), (500, 100), cv.FONT_HERSHEY_SIMPLEX, 1, (0, 0, 255), 2)
            #cv.putText(image, "z: " + str(np.round(z_angle, 2)), (500, 150), cv.FONT_HERSHEY_SIMPLEX, 1, (0, 0, 255), 2)
    
        # Desenha e calcula a relação de aspecto para os olhos e lábios
        draw_landmarks(image, outputs, LEFT_EYE_TOP_BOTTOM, COLOR_GREEN)
        draw_landmarks(image, outputs, LEFT_EYE_LEFT_RIGHT, COLOR_WHITE)
        ratio_left = get_aspect_ratio(image, outputs, LEFT_EYE_TOP_BOTTOM, LEFT_EYE_LEFT_RIGHT)
    
		# Desenha os pontos de referência do olho direito
        draw_landmarks(image, outputs, RIGHT_EYE_TOP_BOTTOM, COLOR_GREEN)
        draw_landmarks(image, outputs, RIGHT_EYE_LEFT_RIGHT, COLOR_WHITE)
        ratio_right = get_aspect_ratio(image, outputs, RIGHT_EYE_TOP_BOTTOM, RIGHT_EYE_LEFT_RIGHT)
    
		# Desenha os pontos de referência dos labios
        draw_landmarks(image, outputs, UPPER_LOWER_LIPS, COLOR_GREEN)
        draw_landmarks(image, outputs, LEFT_RIGHT_LIPS, COLOR_WHITE)
    
		# Calcula a média da relação de aspecto entre os olhos esquerdo e direito
        ratio = (ratio_left + ratio_right) / 2.0
        cv.putText(image, "EAR: {:.2f}".format(ratio), (480, 30), cv.FONT_HERSHEY_SIMPLEX, 0.7, (0, 0, 255), 2)
    
        if ratio > min_toleranceEyes:
            frame_count += 1
            if frame_count > 2:
                cv.putText(image, "Eyes Closed", (10, 30), cv.FONT_HERSHEY_SIMPLEX, 0.7, (0, 0, 255), 2)
        else:
            frame_count = 0
    
        current_time = time.time()
        
        if origem == '3':
            frame_count = min_frameEyes + 1
            
        if frame_count > min_frameEyes and (current_time - last_capture_time) > capture_interval and flagBocejo == 0:
            if process_yolo(clean_image)['sleeping'] >= 50:
                # Dormindo
                cv.putText(image, "DROWSINESS ALERT!", (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.7, (0, 0, 255), 2)
                
                # Salva a imagem capturada
                image_path = f'{ID_DEVICE}_{datetime.datetime.now().strftime("%d-%m-%Y_%H-%M-%S")}.jpg'
                cv.imwrite(image_path, clean_image)
                
                # Atualiza o tempo da última captura
                last_capture_time = current_time
                
                threading.Thread(target=post_image, args=(ID_DEVICE, image_path)).start()
                print("Dormindo")
            
                    
                    
    
		# Calcula se a boca está aberta
        ratio_lips = get_aspect_ratio(image, outputs, UPPER_LOWER_LIPS, LEFT_RIGHT_LIPS)
        cv.putText(image, "MAR: {:.2f}".format(ratio_lips), (480, 60), cv.FONT_HERSHEY_SIMPLEX, 0.7, (0, 0, 255), 2)
    
        if ratio_lips < min_toleranceMouth:
            frame_countM += 1
            # Boca aberta
            cv.putText(image, "Boca aberta", (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.7, (0, 0, 255), 2)
            flagBocejo = 1
        else:
            frame_countM = 0
            flagBocejo = 0
            
        if origem == '3':
            frame_countM = min_frameMouth + 1
    
        if frame_countM > min_frameMouth:
            if process_yolo(clean_image)['yawning'] >= 50:
                # Bocejando
                cv.putText(image, "Bocejo", (10, 80), cv.FONT_HERSHEY_SIMPLEX, 0.7, (0, 0, 255), 2)
                print("Bocejando")
    
    cv.imshow("Drive Watch", image)
    
    # Pressione 'ESC' para sair
    if cv.waitKey(1) & 0xFF == 27:
        break

cv.destroyAllWindows()
try:
    capture.release()
except Exception as e:
        print("")
        
