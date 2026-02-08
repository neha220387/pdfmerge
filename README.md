# PDF Merge Application

A Spring Boot application that merges two PDF files into a single PDF document. This application provides both REST API endpoints and a user-friendly web interface for PDF merging.

## Features

- ✅ Merge two PDF files into one
- ✅ Validate PDF files before merging
- ✅ Web-based UI for easy file upload
- ✅ REST API for programmatic access
- ✅ Download merged PDF directly
- ✅ Error handling and validation
- ✅ Supports files up to 50MB

## Technology Stack

- **Java 17** - Programming language
- **Spring Boot 3.2.0** - Framework
- **Apache PDFBox 3.0.0** - PDF manipulation library
- **Maven** - Build tool
- **HTML5 & CSS3** - Frontend interface

## Project Structure

```
pdfmerge/
├── src/
│   ├── main/
│   │   ├── java/com/pdfmerge/
│   │   │   ├── PdfMergeApplication.java      # Main Spring Boot Application
│   │   │   ├── controller/
│   │   │   │   ├── PdfMergeController.java   # REST API endpoints
│   │   │   │   └── ViewController.java       # Web interface controller
│   │   │   └── service/
│   │   │       └── PdfMergeService.java      # PDF merging logic
│   │   └── resources/
│   │       ├── application.properties         # Application configuration
│   │       └── templates/
│   │           └── index.html                 # Web UI
│   └── test/
│       └── java/...                          # Unit tests
├── pom.xml                                    # Maven configuration
└── README.md                                  # This file
```

## Prerequisites

- Java 17 or higher
- Maven 3.6.0 or higher
- 2GB RAM minimum

## Installation & Setup

### 1. Clone or Navigate to the Project

```bash
cd /workspaces/pdfmerge
```

### 2. Build the Project

```bash
mvn clean install
```

This command will:
- Download all dependencies
- Compile the source code
- Run any unit tests
- Package the application

### 3. Run the Application

```bash
mvn spring-boot:run
```

Or build a JAR and run it:

```bash
mvn clean package
java -jar target/pdf-merge-app-1.0.0.jar
```

The application will start on `http://localhost:8080`

## Usage

### Web Interface

1. Open your browser and navigate to `http://localhost:8080`
2. Click on the "First PDF File" area to select the first PDF
3. Click on the "Second PDF File" area to select the second PDF
4. Click the "Merge PDFs" button
5. The merged PDF will automatically download

### REST API

#### Endpoint: `/api/pdf/merge`

**Method:** POST

**Parameters:**
- `file1` (MultipartFile, required) - First PDF file
- `file2` (MultipartFile, required) - Second PDF file

**Example using cURL:**

```bash
curl -X POST http://localhost:8080/api/pdf/merge \
  -F "file1=@path/to/first.pdf" \
  -F "file2=@path/to/second.pdf" \
  --output merged.pdf
```

**Example using JavaScript/Fetch:**

```javascript
const formData = new FormData();
formData.append('file1', file1Input.files[0]);
formData.append('file2', file2Input.files[0]);

const response = await fetch('/api/pdf/merge', {
  method: 'POST',
  body: formData
});

const blob = await response.blob();
const url = window.URL.createObjectURL(blob);
const a = document.createElement('a');
a.href = url;
a.download = 'merged.pdf';
a.click();
```

**Example using Python:**

```python
import requests

files = {
    'file1': open('first.pdf', 'rb'),
    'file2': open('second.pdf', 'rb')
}

response = requests.post('http://localhost:8080/api/pdf/merge', files=files)

with open('merged.pdf', 'wb') as f:
    f.write(response.content)

files['file1'].close()
files['file2'].close()
```

#### Health Check Endpoint: `/api/pdf/health`

**Method:** GET

**Response:** `PDF Merge Service is running`

## Configuration

Edit `src/main/resources/application.properties` to customize:

```properties
# Server port (default: 8080)
server.port=8080

# Maximum file upload size (default: 50MB)
spring.servlet.multipart.max-file-size=50MB

# Maximum request size (default: 50MB)
spring.servlet.multipart.max-request-size=50MB

# Logging level (default: INFO)
logging.level.root=INFO
logging.level.com.pdfmerge=DEBUG
```

## Error Handling

The application handles the following errors:

- **Missing Files:** Returns HTTP 400 with message "First/Second PDF file is required"
- **Invalid PDF Format:** Returns HTTP 400 with message "File is not a valid PDF"
- **Processing Error:** Returns HTTP 500 with error details
- **Invalid Content Type:** Returns HTTP 400

## API Response Examples

### Success Response
```
HTTP/1.1 200 OK
Content-Type: application/pdf
Content-Disposition: attachment; filename="merged.pdf"

[PDF binary data]
```

### Error Response
```json
{
  "error": "First file is not a valid PDF"
}
```

## Limitations

- Maximum file size: 50MB per file
- Only supports PDF format
- Merges files sequentially (file1 pages before file2 pages)
- Maximum 2 files per merge request

## Performance Considerations

- Merging speed depends on PDF file sizes and system resources
- Average merge time: 1-5 seconds for typical PDFs
- Temporary files are created in system temp directory

## Troubleshooting

### Application won't start
- Ensure Java 17 is installed: `java -version`
- Check if port 8080 is available: `lsof -i :8080`
- Increase heap memory: `JAVA_OPTS="-Xmx1024m" mvn spring-boot:run`

### Merge fails with "Invalid PDF"
- Verify the PDF files are not corrupted
- Try opening the PDF in a PDF reader
- Check file permissions

### Slow merge performance
- Close other resource-intensive applications
- Increase JVM heap memory
- Reduce PDF file sizes if possible

## Future Enhancements

- [ ] Support merging more than 2 PDFs
- [ ] Reorder pages before merging
- [ ] Add PDF compression
- [ ] Database integration for merge history
- [ ] Batch processing support
- [ ] Progress tracking for large files
- [ ] PDF password protection

## Dependencies

### Core Dependencies
- `spring-boot-starter-web` - Web framework
- `pdfbox` - PDF manipulation
- `lombok` - Code generation

### Test Dependencies
- `spring-boot-starter-test` - Testing framework

## License

This project is open source and available under the MIT License.

## Support

For issues, questions, or contributions, please feel free to create an issue or pull request.

## Author

PDF Merge Team

---

**Last Updated:** February 2026
**Version:** 1.0.0