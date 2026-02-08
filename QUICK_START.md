# Quick Start Guide

## 1️⃣ Build the Project

```bash
cd /workspaces/pdfmerge
mvn clean install
```

**Expected output:** `BUILD SUCCESS`

## 2️⃣ Run the Application

```bash
mvn spring-boot:run
```

**Expected output:** 
```
...
Started PdfMergeApplication in X.XXX seconds
```

## 3️⃣ Access the Application

Open your browser and go to:
```
http://localhost:8080
```

## 4️⃣ Test the Merge Feature

1. Click "First PDF File" and select a PDF
2. Click "Second PDF File" and select another PDF
3. Click "Merge PDFs"
4. The merged PDF will download automatically

---

## 📝 Quick API Test (Using cURL)

```bash
curl -X POST http://localhost:8080/api/pdf/merge \
  -F "file1=@/path/to/first.pdf" \
  -F "file2=@/path/to/second.pdf" \
  --output merged.pdf
```

---

## ✅ Health Check

```bash
curl http://localhost:8080/api/pdf/health
```

**Response:** `PDF Merge Service is running`

---

## 🛑 Stop the Application

Press `Ctrl + C` in the terminal running the application.

---

## 📦 Build JAR for Deployment

```bash
mvn clean package
```

Then run:
```bash
java -jar target/pdf-merge-app-1.0.0.jar
```

---

## 🔧 Troubleshooting

**Port 8080 already in use?**
```bash
lsof -i :8080
kill -9 <PID>
```

Or change the port in `src/main/resources/application.properties`:
```properties
server.port=9090
```

**Build fails?**
```bash
# Clear Maven cache
rm -rf ~/.m2/repository
mvn clean install -U
```

---

For detailed information, see [README.md](README.md)
