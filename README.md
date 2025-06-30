# 🛍️ ecommerce-kafka

## 📝 Descrição
A aplicação é um sistema de e-commerce construído com uma arquitetura de microsserviços que se comunicam através do Kafka. O código é organizado em um repositório monolito dividido em módulos Maven, cada um representando um microsserviço (service-new-order, service-fraud-detector, service-email e service-log) e uma biblioteca compartilhada (common-kafka). A serialização e desserialização são usadas para converter objetos Java em mensagens Kafka, e a classe Order representa os pedidos de compra, sendo que cada microsserviço eventualmente possui sua própria versão para evitar acoplamento.

## 🚀 Serviços

### 📦 service-new-order
Gera 10 vendas, resultando em 10 mensagens de nova ordem e 10 mensagens de e-mail.

### 🔍 service-fraud-detector
Recebe as 10 mensagens de nova ordem para análise de fraude.

### 📧 service-email
Recebe os 10 e-mails para envio.

### 📝 service-log
Recebe e registra todas as 20 mensagens.