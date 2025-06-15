# Detecção de Sonolência com YOLO - TCC de Engenharia de Computação

Este repositório contém o código e os arquivos necessários para a aplicação de YOLO (*You Only Look Once*) em um projeto de TCC de Engenharia de Computação. O objetivo é construir um modelo de detecção de sonolência em automóveis utilizando um Raspberry Pi. O YOLO foi escolhido por ser uma tecnologia otimizada e eficiente para este tipo de equipamento.

## Descrição do Projeto

A partir de uma base de dados, podemos gerar um modelo que utiliza aprendizado de máquina para a detecção de determinados objetos. Neste repositório, temos um modelo treinado para nossa versão de detecção de fadiga/sonolência através de uma câmera conectada ao Raspberry Pi.

Todas as evoluções e melhorias do projeto, visando aumentar a precisão ou não, estão sendo adicionadas dentro da pasta `train`.

A pasta `exp` representa um experimento, e dentro dela existe o arquivo `weights` que contém os modelos treinados. Sempre haverá o `last.pt`, que representa o experimento atual, e o `best.pt`, que é o melhor modelo baseado nos dados.

## Base de Dados

A base de dados utilizada está disponível no Kaggle no link: **[Kaggle Dataset Link]([#](https://www.kaggle.com/datasets/nexuswho/drowsiness-detection))**.

## Executando e Testando o Processo

Para executar e testar o processo, podemos usar o arquivo Jupyter Notebook disponível, seguindo o passo a passo. Para treinar o modelo, é necessário ter uma base de dados. Podemos utilizar os pesos dos treinamentos anteriores ou fazer do zero.

### Treinamento do Modelo

Para treinar o modelo, utilize o comando:

```bash
!cd yolov5 && python train.py --img 320 --batch 16 --epochs 100 --data dataset.yml --workers 2
```

### Utilizando Pesos Treinados

Para utilizar os pesos dos treinamentos anteriores, use o comando:

```bash
--weights runs/train/exp/weights/best.pt
```

O valor após `--weights` define qual modelo será utilizado.

### Estrutura do Arquivo `dataset.yml`

Dentro da pasta `yolov5`, há um arquivo chamado `dataset.yml`, que contém informações sobre os nomes e diretórios das imagens. Ao fazer o treinamento de um modelo, é necessário indicar ali os diretórios corretos.

## Arquivos de Extensão `.pt`

Os arquivos de extensão `.pt` são modelos treinados. Eles são utilizados para carregar os pesos do modelo durante a inferência ou para continuar o treinamento.

## Estrutura do Jupyter Notebook

O Jupyter Notebook contém as seguintes seções:

1. **Instalar e Importar Dependências**: Instalação das bibliotecas necessárias.
2. **Clonar Repositório YOLOv5**: Clonagem do repositório YOLOv5 do GitHub.
3. **Instalar Requisitos do YOLOv5**: Instalação das dependências listadas no arquivo `requirements.txt`.
4. **Importar Bibliotecas Necessárias**: Importação das bibliotecas essenciais para o projeto.
5. **Treinar o Modelo do Zero**: Treinamento do modelo YOLOv5 do zero usando um conjunto de dados personalizado.
6. **Carregar Modelo Personalizado**: Carregamento do modelo YOLOv5 personalizado treinado.
7. **Carregar Imagem de Teste**: Carregamento de uma imagem de teste para realizar a detecção de objetos.
8. **Obter Resultados do Modelo**: Obtenção dos resultados da detecção de objetos na imagem de teste.
9. **Exibir Resultados**: Exibição dos resultados da detecção de objetos na imagem de teste usando Matplotlib.
10. **Inicializar e Processar Vídeo da Câmera**: Inicialização da câmera e processamento do vídeo em tempo real para detecção de sonolência usando o modelo YOLOv5.

# Detecção de Sonolência Lógica Fuzzy

Este projeto realiza a detecção de sonolência em tempo real utilizando uma câmera ou processando uma imagem estática. Ele emprega técnicas de visão computacional e inteligência artificial para analisar sinais de fadiga em um indivíduo.

## Requisitos

Certifique-se de ter o Python instalado e execute o seguinte comando para instalar as dependências necessárias:

```bash
pip install opencv-python numpy mediapipe torch scipy pyttsx3 scikit-fuzzy matplotlib
```

## Como Usar

### Execução com Vídeo ao Vivo

Para executar a detecção em tempo real usando a câmera, escolha o modo no main do arquivo fuzzy_logic.py `video`. 
Modifique o índice da câmera no arquivo principal caso necessário:

```bash
cap = cv2.VideoCapture(0)  # 0 para câmera padrão
```
Execute:

```bash
python fuzzy_logic.py
```
ou:

```bash
py fuzzy_logic.py
```
### Execução com Imagem
Para executar a detecção em tempo real usando a câmera, escolha o modo no main do arquivo fuzzy_logic.py `image`. 
Defina o caminho:
```bash
 IMAGE_PATH = "img_test.jpg"  # Defina o caminho da imagem caso use o modo "image"
```
Execute:

```bash
python fuzzy_logic.py
```
ou:

```bash
py fuzzy_logic.py
```
## Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir *issues* e *pull requests* para melhorias e correções.

## Licença

Este projeto está licenciado sob a **MIT License**.

---

Para mais informações, consulte a documentação do YOLOv5 e os tutoriais disponíveis no repositório oficial do [YOLOv5](https://github.com/ultralytics/yolov5).
