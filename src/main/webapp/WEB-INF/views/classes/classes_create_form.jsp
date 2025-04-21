<!-- Nhúng nội dung file header  -->
<jsp:include page="../header.jsp">
    <jsp:param name="pageActive" value="user" />
</jsp:include>


<style>
        body {
            background-color: #f8f9fa;
        }
        .form-container {
            max-width: 600px;
            margin: 50px auto;
            padding: 30px;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .form-label {
            font-weight: bold;
        }
        .btn {
            width: 100%;
        }
    </style>

    <div class="container">
        <div class="form-container">
            <h2 class="text-center text-primary mb-4">Thêm lớp</h2>
            <form:form method="post" action="save" modelAttribute="classes">
                <div class="mb-3">
                    <label for="className" class="form-label">Tên lớp:</label>
                    <form:input path="className" class="form-control" placeholder="Nhập tên lớp" />
                </div>
              <div class="mb-3 form-check">
        <form:checkbox path="status" class="form-check-input" id="status" />
        <label for="status" class="form-check-label">lớp đang hoạt động</label>
    </div>
              
                <button type="submit" class="btn btn-success">Lưu</button>
            </form:form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
