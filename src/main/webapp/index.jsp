<!DOCTYPE html>

<%@page import="com.wizecommerce.cts.utils.Hibernate"%>
<%@page import="com.wizecommerce.cts.utils.ChangeRecord"%>
<%@page import="com.wizecommerce.cts.utils.Source"%>
<%@page import="java.util.Iterator"%>

<html lang="en">
  <head>
    <title>Change Tracking</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <link rel="stylesheet" href="resources/css/bootstrap.css" media="screen">
    <link rel="stylesheet" href="resources/css/bootswatch.min.css">
    
    <script src="resources/js/jquery-1.10.2.min.js"></script>
    <script src="resources/js/bootstrap.min.js"></script>
    <script src="resources/js/bootswatch.js"></script>
  </head>
  <body>
    
    <div class="navbar navbar-default navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <a href="#" class="navbar-brand">Change Tracking</a>
          <button class="navbar-toggle" type="button" data-toggle="collapse" data-target="#navbar-main">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
        </div>
        <div class="navbar-collapse collapse" id="navbar-main">
          <ul class="nav navbar-nav">
            <li class="dropdown">
              <a class="dropdown-toggle" data-toggle="dropdown" href="#" id="preferences">Preferences <span class="caret"></span></a>
              <ul class="dropdown-menu" aria-labelledby="themes">
                <li><a tabindex="-1" href="#">General</a></li>
                <li class="divider"></li>
                <li><a tabindex="-1" href="#">Changes Per Page</a></li>
              </ul>
            </li>
          </ul>

          <ul class="nav navbar-nav navbar-right">
            <li><a href="#" target="_blank">Sign In</a></li>
            <li><a href="http://graphite2.pv.sv.nextag.com/cubism/dashboard/main-dashboard.html" target="_blank">Cubism</a></li>
          </ul>

        </div>
      </div>
    </div>

	<%
		Integer startOffset = 0, maxResult = 10;
		
		if(request.getParameter("startOffset") != null) {
			startOffset =  Integer.parseInt(request.getParameter("startOffset"));
		}
		if(request.getParameter("maxResult") != null) {
			maxResult =  Integer.parseInt(request.getParameter("maxResult"));
		}
	%>
    <div class="container">

      <div class="page-header" id="banner">
        <div class="row">
          <div class="col-lg-6">
            <h3>Feeds</h3>
            <div class="bs-example table-responsive">
              <table class="table table-striped table-bordered table-hover">
                <thead>
                  <tr>
                    <th nowrap>#</th>
                    <th nowrap>Change Description</th>
                    <th nowrap>Source</th>
                    <th nowrap>Sub Source</th>
                    <th nowrap>Status</th>
                    <th nowrap>DateTime</th>
                  </tr>
                </thead>
                <tbody>
                <% 
            	Hibernate hibernate = new Hibernate();
            	Iterator<?> changeInfoIterator = hibernate.executeSelectQuery("FROM ChangeRecord as C ORDER BY sourceDatetime DESC", startOffset, maxResult);
            	while(changeInfoIterator.hasNext()) {
            		ChangeRecord entry = (ChangeRecord) changeInfoIterator.next();
            	%>
					<tr>
	                    <td><%=entry.getCrId() %></td>
	                    <td nowrap><%=entry.getDescription() %></td>
	                    <td nowrap><%=entry.getSourceName() %></td>
	                    <td nowrap><%=entry.getSubSourceName() %></td>
	                    <!-- <td><span class="label label-success">Success</span></td>  -->
	                    <td nowrap><span class="<%=(entry.getStatus().equalsIgnoreCase("SUCCESS") || entry.getStatus().equalsIgnoreCase("COMPLETED"))?
	                    		"label label-success":"label label-danger"%>"><%=entry.getStatus() %></span></td>
	                    <td nowrap><%=entry.getSourceDatetimeString() %></td>
					</tr>
            	<%
	            	}
    	        %>
                  </tbody>
              </table>
              <ul class="pagination pagination-sm">
                <li class="disabled"><a href="#">&laquo;</a></li>
                <li class="active"><a href="./?maxResult=50&startOffset=5">1</a></li>
                <li><a href="./?maxResult=50&startOffset=25">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">5</a></li>
                <li><a href="#">&raquo;</a></li>
              </ul>
            </div>
            
          </div>
        </div>
      </div>

      <footer>
        <div class="row">
          <div class="col-lg-12">
            <p>Change Tracking System</p>
          </div>
        </div>
        
      </footer>
    </div>

  </body>
</html>