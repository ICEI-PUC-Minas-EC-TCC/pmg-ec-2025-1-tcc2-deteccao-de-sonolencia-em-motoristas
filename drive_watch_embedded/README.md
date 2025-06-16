# DriveWatch
Drive Watch é uma solução voltada para a segurança automotiva, utilizando tecnologias de Inteligência Artificial (IA) para monitorar e detectar situações que comprometam a segurança do motorista, como sonolência ao volante. O sistema é ideal para empresas de gestão de frotas, abrangendo desde transportadoras até seguradoras. Ele funciona com base em um Raspberry Pi, equipado com uma webcam que captura imagens do motorista em tempo real, as quais são processadas localmente para identificar sinais de fadiga. Quando detectados, o sistema emite alertas sonoros para chamar a atenção do motorista, ao mesmo tempo em que registra as ocorrências em um banco de dados em nuvem (AWS MySQL), possibilitando o acesso remoto aos gestores por meio de uma aplicação web desenvolvida com Vue JS e Spring Boot. A arquitetura do Drive Watch é composta por quatro blocos principais: hardware, banco de dados, back-end e front-end, todos interligados para garantir o funcionamento do sistema. O hardware é complementado por um buzzer/auto falante de alerta e uma fonte de alimentação que garantem sua operação contínua dentro do veículo.

# Instruções para compilação, execução e testes (How to Build)
Esta seção se refere especificamente ao  arquivo 'FileWatch.py' responsável por rodar testes com
imagens obtidas de datasets. Para seu correto funcionamento, algumas operações devem ser feitas
de forma 'manual' para obtenção dos datasets (justificativa: Datasets pesam cerca de 2-3 GBs e incluí-los
neste repositório é inviável).

Assim sendo, objetivando-se replicar os experimentos descritos no artigo, siga as seguintes instruções:

- Realize download dos datasets de imagens estáticas listados na seção "Datasets de Imagens Estáticas" com exceção do dataset descrito como "treinamento YOLO"
- Ao realizar o download, crie a pasta 'kaggleset' no mesmo diretório onde estiver o arquivo FileWatch.py
- Depois de baixar <a href='https://www.kaggle.com/datasets/rakibuleceruet/drowsiness-prediction-dataset/data'>este dataset</a> recorte as pastas 'Active Subjects' e 'Fatigue Subjects' para a pasta kaggleset.
- ATENÇÃO IMPORTANTE: renomeie 'Active Subjects' para 'awake' e 'Fatigue Subjects' para 'sleepy'. Os nomes devem ser EXATAMENTE estes pois serão utilizados como 'labels' no código implementado.
- Depois de baixar <a href='https://www.kaggle.com/datasets/dheerajperumandla/drowsiness-dataset'>este dataset</a> recorte as pastas 'yawn' e 'no_yawn' para 'kaggleset'.
- Não é necessário renomear as pastas 'yawn' e 'no_yawn' o codigo ja trata isto!
- Abra um terminal na mesma pasta do FileWatch.py e certifique-se de obter as dependências: "pip install mediapipe opencv-python scipy requests numpy torch scikit-fuzzy matplotlib pandas scikit-learn"
- Depois de instaladas, o arquivo FileWatch.py pode ser executado através do python.
- Siga o processo que aparecer no terminal, selecionando a opção de imagens.
- Após rodados os testes, o resultado aparece em um arquivo chamado 'report.txt'.
- O par de valores (esperado,obtido) aparece no arquivo 'results.csv'.
- Os dois arquivos serão gerados no mesmo diretório de 'FileWatch.py'.

O arquivo DriveWatch.py foi arquitetado para rodar em um Raspberry Pi.


# Datasets

Referencias de datasets utilizados no projeto:

## Datasets de Imagens Estáticas
Os seguintes datasets foram utilizados nos testes com imagens estáticas:
- https://www.kaggle.com/datasets/rakibuleceruet/drowsiness-prediction-dataset/data
- https://www.kaggle.com/datasets/dheerajperumandla/drowsiness-dataset

Os seguintes datasets foram utilizados no treinamento YOLO:
- https://www.kaggle.com/datasets/nexuswho/drowsiness-detection


## Datasets de Videos
Não foram utilizados, mas interessantes:
- https://www.kaggle.com/datasets/rishab260/uta-reallife-drowsiness-dataset