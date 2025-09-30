# Back_End
# Backend - FaceShield_Back

Este repositório contém o backend do sistema FaceShield, responsável pela lógica de negócios, acesso a dados e controle da aplicação.

---

## 📁 Estrutura do Projeto

```bash
FaceShield_Back/
├── .idea/
├── src/
│ └── main/
│ └── java/
│ └── com.example.FaceShield_Back/
│ ├── Config/
│ ├── Controller/
│ ├── DTO/
│ ├── Entity/
│ ├── Repository/
│ ├── Service/
│ └── FaceShieldBackApplication.java
```
##Descrição das Pastas

| Pasta       | Descrição                                                     |
|-------------|---------------------------------------------------------------|
| `Config/`    | Classes de configuração do Spring Boot e do projeto          |
| `Controller/`| Controladores REST responsáveis por receber requisições HTTP |
| `DTO/`       | Data Transfer Objects para transportar dados entre camadas   |
| `Entity/`    | Classes que representam as entidades do banco de dados       |
| `Repository/`| Interfaces para acesso aos dados (ex: JPA repositories)      |
| `Service/`   | Regras de negócio e lógica intermediária                      |
| `FaceShieldBackApplication.java` | Classe principal para inicializar a aplicação Spring Boot |


# GitFlow

### Feature = Task
### Hotfix = Debugar na main (urgente!)
### Release = Debugar na develop (não é urgente)
### Develop = Branch de teste (antes da main)
