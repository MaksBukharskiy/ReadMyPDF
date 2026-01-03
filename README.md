# ReadMyPDF 🤖

A Telegram bot built with Spring Boot that extracts text from PDF files. Simply send a PDF, get the text back!

## 🚀 Features  
- **PDF Text Extraction** - Extract plain text from PDF documents  
- **Telegram Integration** - Easy-to-use interface through Telegram  
- **Spring Boot Backend** - Robust and scalable architecture  
- **Modern Tech Stack** - Java 21, Spring Boot 4.x, PostgreSQL

## 📋 Tech Stack  
- **Backend**: Java 21, Spring Boot 4.x  
- **Database**: PostgreSQL  
- **PDF Processing**: Apache PDFBox  
- **Telegram API**: TelegramBots library  
- **Build Tool**: Maven

## 🏗️ Project Structure  
```  
ReadMyPDF/  
├── src/main/java/com/maks/readmypdf/  
│ ├── config/ # Configuration classes  
│ ├── bot/ # Telegram bot logic  
│ ├── service/ # Business logic (PDF processing)  
│ ├── entity/ # Database entities  
│ └── repository/ # Data access layer  
├── src/main/resources/  
│ ├── application.yml # Configuration  
│ └── db/migration/ # Database migrations  
└── docker-compose.yml # Local development setup
```

## 🚦 Quick Start

### Prerequisites  
- Java 21+  
- Maven 3.9+  
- Docker & Docker Compose  
- Telegram Bot Token (from @BotFather)


## 🗺️ Development Roadmap

### ✅ Day 1: Foundation  
- [x] Project setup with Spring Boot  
- [x] Telegram bot integration  
- [x] Basic command handling (/start, /help)

### 📅 Day 2: Core Features  
- [x] PDF file reception and validation  
- [x] Text extraction with Apache PDFBox  
- [x] Error handling and user feedback

### 📅 Day 3: Data Persistence  
- [x] PostgreSQL database setup  
- [x] User and file processing history  
- [x] Statistics tracking

### 📅 Day 4: Advanced Features  
- [] Multi-language support  
- [x] File format validation  
- [x] Batch processing

### 📅 Day 5: Polish & Deploy  
- [x] Docker containerization  
- [x] Logging and monitoring  
- [x] Deployment configuration

## 🤝 Contributing

1. Fork the repository  
2. Create a feature branch (`git checkout -b feature/amazing-feature\`)  
3. Commit changes (`git commit -m 'Add amazing feature'\`)  
4. Push to branch (`git push origin feature/amazing-feature\`)  
5. Open a Pull Request

## 🙏 Acknowledgments

- [Spring Boot](https://spring.io/projects/spring-boot) - The amazing framework  
- [Telegram Bot API](https://core.telegram.org/bots/api) - For bot capabilities  
- [Apache PDFBox](https://pdfbox.apache.org/) - PDF processing library

## 📞 Support

For questions or support:  
- 📧 Email: maksbuharskij16@gmail.com  
- 💬 Telegram: @freedom_lovver  

---

## Made with ❤️ by Maks Bukharskiy
Java Developer | Backend Enthusiast | Problem Solver

 
**"Transforming PDFs into plain text, one bot message at a time"** 📄➡️