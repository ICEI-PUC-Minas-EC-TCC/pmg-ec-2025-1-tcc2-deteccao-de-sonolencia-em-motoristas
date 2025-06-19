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
