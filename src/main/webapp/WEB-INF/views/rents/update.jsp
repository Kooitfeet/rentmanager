<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                Reservations
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <!-- Horizontal Form -->
                    <div class="box">
                        <!-- form start -->
                        <form class="form-horizontal" method="post">
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="vehicule" class="col-sm-2 control-label">Voiture</label>

                                    <div class="col-sm-10">
                                        <select class="form-control" id="vehicule" name="vehicule">
                                             <c:forEach items="${vehicules}" var="vehicule">
                                                <option value="${vehicule.getId()}">${vehicule.getConstructeur()} ${vehicule.getModele()}</option>
                                            </c:forEach>
                                            <option value="${reservation.getIdVehicle()}" selected>${reservation.getVehicle().getConstructeur()} ${reservation.getVehicle().getModele()}</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="client" class="col-sm-2 control-label">Client</label>

                                    <div class="col-sm-10">
                                        <select class="form-control" id="client" name="client">
                                             <c:forEach items="${clients}" var="client">
                                                <option value="${client.getId()}">${client.getPrenom()} ${client.getNom()}</option>
                                            </c:forEach>
                                            <option value="${reservation.getIdClient()}" selected>${reservation.getClient().getPrenom()} ${reservation.getClient().getNom()}</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="begin" class="col-sm-2 control-label">Date de debut</label>

                                    <div class="col-sm-10">
                                        <input type="date" class="form-control" id="debut" name="debut" placeholder="Date de début"
                                        value="${reservation.getDateDebut()}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="end" class="col-sm-2 control-label">Date de fin</label>

                                    <div class="col-sm-10">
                                        <input type="date" class="form-control" id="fin" name="fin" placeholder="Date de fin"
                                        value="${reservation.getDateFin()}">
                                    </div>
                                </div>
                            </div>
                            <!-- /.box-body -->
                            <div class="box-footer">
                                <button type="submit" class="btn btn-info pull-right">Modifier</button>
                            </div>
                            <!-- /.box-footer -->
                        </form>
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
            </div>
        </section>
        <!-- /.content -->
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
<script src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.extensions.js"></script>
<script>
    $(function () {
        $('[data-mask]').inputmask()
    });
</script>
</body>
</html>
