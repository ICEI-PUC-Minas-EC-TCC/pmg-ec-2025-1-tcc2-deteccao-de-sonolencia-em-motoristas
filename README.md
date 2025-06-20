# Drive Watch - Um Sistema Embarcado com Inteligência Artificial para Monitoramento em Tempo Real e Detecção de Sonolência em Motoristas

## Autores
- Christian David Costa Vieira
- Cristian Fernandes Sena
- Dayane Gabriela Santos Cordeiro
- Gabriel Henrique Braz
- Guilherme Marcos Pereira Gonçalves

## Orientador
- Felipe Augusto Lara Soares

## Sobre o projeto
Drive Watch é um sistema embarcado desenvolvido como Trabalho de Conclusão de Curso (TCC) em Engenharia de Computação na PUC Minas. O objetivo é detectar sinais de sonolência em motoristas em tempo real, utilizando um Raspberry Pi com câmera e técnicas de visão computacional e inteligência artificial.

## Execução
A seguir encontram-se os passos para executar cada parte do projeto:

### Pré requisitos
1. Clone este repositório:
```
git clone https://github.com/ICEI-PUC-Minas-EC-TCC/pmg-ec-2025-1-tcc2-deteccao-de-sonolencia-em-motoristas.git
cd pmg-ec-2025-1-tcc2-deteccao-de-sonolencia-em-motoristas
```

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

### Como executar a aplicação front-end

Requer: Node JS, NPM.

1) Clonar repositório em qualquer pasta. [git clone <URL>]
2) Execute no terminal, na pasta raiz do projeto: 'npm install' e 'npm update'.
3) Se tudo estiver certo, a build de desenvolvimento pode ser aberta com 'npm run dev'.
4) Acesse o endereço que aparecer no terminal e teste.
5) É possível buildar para produção usando 'npm run build'.
6) É possível testar a dist de produção com 'npx vite preview'.


### drive_watch_backend
Essa pasta contém o back-end construído para o site implementado. A aplicação foi construída em Java juntamente do framework Spring Boot.
Antes de começar, você vai precisar ter instalado em sua máquina:
* Java 17+
* Maven 3.8+

Para executar de forma local execute os passos:

1. Entre na pasta correspondente:
```
cd drive_watch_backend
```

2. Compile o projeto:
```
mvn clean install
```

3. Execute a aplicação:
```
mvn spring-boot:run
```

A aplicação será iniciada na porta padrão `http://localhost:8080`.
