# 📚 EqualPath API – Plataforma Inteligente de Evolução Profissional

API corporativa desenvolvida para orquestrar trilhas de carreira, skills, cursos recomendados e recomendações personalizadas alinhadas ao desenvolvimento contínuo dos usuários.


# 🚀 Visão Geral da Solução

A EqualPath API consolida os pilares da jornada profissional do usuário, integrando trilhas estratégicas, competências técnicas/comportamentais e cursos recomendados.
A arquitetura foi estruturada com foco em escalabilidade, baixa acoplagem, segurança via JWT, padronização REST e alta manutenibilidade, conforme diretrizes do ciclo de desenvolvimento ágil.

# 🏗️ Arquitetura da Aplicação

A solução utiliza uma abordagem baseada em camadas:

Controller Layer – Interfaces REST, versionamento e documentação via Swagger.

Service Layer – Regras de negócio, orquestração de domínios, transações.

Repository Layer – Abstração de persistência via Spring Data JPA.

Domain Layer – Modelagem orientada ao domínio (DDD-lite).

Exception Layer – Tratamento centralizado via GlobalExceptionHandler.

Toda a API segue padrões como:

DTOs para comunicação,

Validação Bean Validation (Jakarta),

Enum mapeado via @Enumerated,

Tratamento de erros padronizado com NotFoundException e retorno estruturado.