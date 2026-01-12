"# TaskFlow-Management-System-backend" 
一個任務與工作流程管理系統後端，使用 Spring Boot + JPA + MySQL 實作，提供 RESTful API 供前端使用。

主要功能：
- 使用者管理 (登入 / 註冊 / 角色權限)
- 任務 CRUD
- 工作流程管理 (Workflow / Status / Task History)
- 通知系統 (Notification)
- JWT 認證與授權
- 排程功能 (Task 過期提醒、自動狀態更新)

技術棧：
- Java 17
- Spring Boot
- JPA / Hibernate
- MySQL
- Redis (可選，通知或暫存)

架構示意：
Frontend (React) <---> REST API (Spring Boot) <---> MySQL / Redis

API 說明 (Endpoints)
User:
- POST /api/users/register
- POST /api/users/login

Task:
- GET /api/tasks
- POST /api/tasks
- PUT /api/tasks/{id}
- DELETE /api/tasks/{id}

Workflow / Status:
- GET /api/workflows
- POST /api/workflows

開發流程 (Development Workflow)
- commit 類型規範：
  - feat: 新功能
  - chore: 設定 / 初始化 / 文件
  - refactor: 重構
  - fix: Bug 修正
- 開發順序：
  1. 規劃 & 設計
  2. 資料庫 + Entity
  3. Service & Controller
  4. 前端 API 對接
  5. 整合 & 測試