<!DOCTYPE HTML>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">

    <title>folxShop</title>

    <link href="static/css/bootstrap.min.css" rel="stylesheet">
    <link href="static/css/style.css" rel="stylesheet">
    <script src="static/js/bootstrap.min.js"></script>

</head>
<body>

    <div role="navigation">
        <div class="navbar navbar-inverse">
            <a href="home" class="navbar-brand">Sample</a>
            <div class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li><a href="allProducts">All Products</a> </li>
                    <li><a href="newProduct">New Products</a> </li>
                </ul>
            </div>
        </div>
    </div>

    <c:choose>
        <c:when test="${mode == 'MODE_HOME'}">
            <div class="container" id="homeDiv">
                <div class="jumbotron text-center">
                    <h1>Welcome to FolxShop</h1>
                </div>
            </div>
        </c:when>
        <c:when test="${mode == 'MODE_PRODUCTS'}">
            <div class="container text-center" id="productsDiv">
                <h3>My Products</h3>
                <hr>
                <div class="table-responsive">
                    <table class="table table-striped table-bordered text-left">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Price</th>
                            <th>Status</th>
                            <th>Creation</th>
                            <th></th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="product" items="${products}">
                            <tr>
                                <td>${product.id}</td>
                                <td>${product.name}</td>
                                <td>${product.price}</td>
                                <td>${product.status}</td>
                                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${product.dateCreated}"/></td>
                                <td>
                                    <a href="updateProduct?id=${product.id}">
                                        <span class="glyphicon glyphicon-pencil"></span>
                                    </a>
                                </td>
                                <td>
                                    <a href="deleteProduct?id=${product.id}">
                                        <span class="glyphicon glyphicon-trash"></span>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:when>
        <c:when test="${mode == 'MODE_NEW' || mode == 'MODE_UPDATE'}">
            <div class="container text-center">
                <h3>Manage Product</h3>
                <hr>
                <c:choose>
                <c:when test="${mode == 'MODE_UPDATE'}">
                  <form class="form-horizontal" method="POST" action="saveProduct">
                     <input type="hidden" name="id" value="${product.id}">
                </c:when>
                <c:when test="${mode == 'MODE_NEW'}">
                  <form class="form-horizontal" method="POST" action="createProduct">
                     <input type="hidden" name="product.id" value="${product.id}">
                </c:when>
                </c:choose>
                    <div class="form-group">
                        <label class="control-label col-md-3">Name</label>
                        <div class="col-md-7">
                            <input type="text" class="form-control" name="name" value="${product.name}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3">Price</label>
                        <div class="col-md-7">
                            <input type="number" class="form-control" name="price" value="${product.price}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3">Status</label>
                        <div class="col-md-7">
                            <%--<input type="text" class="form-control" name="status" value="${product.status}">--%>
                            <select name="status" class="form-control">
                                <option value="INSTOCK">INSTOCK</option>
                                <option value="OUTOFSTOCK">OUTOFSTOCK</option>
                                <option value="WITHDRAWN">WITHDRAW</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <input type="submit" class="btn btn-primary" value="Save">
                    </div>
                </form>
            </div>
        </c:when>
    </c:choose>
</body>
</html>