<%@page import="com.wizecommerce.cts.utils.Hibernate"%>
<%@page import="com.wizecommerce.cts.utils.ChangeRecord"%>
<%@page import="com.wizecommerce.cts.utils.CTSDate"%>
<%@page import="java.util.Iterator"%>

<%@include file="header.jsp" %>
<%
        Integer p = 0;
        String prev_class = "previous";
        if(request.getParameter("p") != null) {
                p =  Integer.parseInt(request.getParameter("p"));
        }
        if(p < 1) {
                prev_class += " disabled";
                p = 1;
        }
%>
<script src="resources/js/d3.v3.min.js" type="text/javascript" charset="utf-8"></script>
<div class="container">
	<div class="jumbotron">
       	<p>This space will hold the some upper level stats </p>
	</div>
     <div class="page-header" id="banner">
       <div class="row">
         <div class="col-lg-6">
           <!-- Table was here  -->
           <div class="list-group">
           		<div class="form-group">
           		<form class="bs-example form-horizontal" method="post">
                  <div class="input-group">
                    <span class="input-group-addon">#</span>
                    <input type="text" class="form-control" placeholder="Search Events" name="kw" id="kw">
                    <span class="input-group-btn">
                      <button class="btn btn-default" type="submit">Search</button>
                    </span>
                  </div>
                  </form>
                </div>
		<%
        CTSDate ctsDate = new CTSDate();
        Hibernate hibernate = new Hibernate();
        Iterator<?> userSettingIterator = hibernate.executeSelectQuery("FROM UserSettings WHERE uId = 1", true);
    	UserSettings userSetting = (UserSettings) userSettingIterator.next();
    	String whereStr = "";
    	if(request.getParameter("kw") != null) {
    		whereStr = "WHERE description LIKE '%" + request.getParameter("kw") + "%' ";
    	}
        Iterator<?> changeInfoIterator = hibernate.executeSelectQuery("FROM ChangeRecord " + whereStr + " ORDER BY sourceDatetime DESC", p * userSetting.getRecordsPerPage() , userSetting.getRecordsPerPage());
        hibernate.terminateSession();
                while(changeInfoIterator.hasNext()) {
                        ChangeRecord entry = (ChangeRecord) changeInfoIterator.next();
        %>
           
                <a href="./detailChange.jsp?id=<%=entry.getCrId() %>" target = "_blank" class="list-group-item">
                  <span class="list-group-item-heading"><%=entry.getDescription() %></span>
                  <span class="<%=(entry.getStatus().equalsIgnoreCase("SUCCESS") || entry.getStatus().equalsIgnoreCase("COMPLETED"))?
                                "label label-success":"label label-danger"%>"><%=entry.getStatus() %>
                  </span>
                  <p class="list-group-item-text">Source : <%=entry.getSourceName() %></p>
                  <p class="list-group-item-text">Sub Source : <%=entry.getSubSourceName() %></p>
                  <p class="list-group-item-text">Source Datetime : <%=ctsDate.epochToString(Integer.parseInt(entry.getSourceDatetimeString()))  %></p>
                </a>
        <%
                }
        %>
              </div>
           
           <div class="pull-right">
                        <ul class="pager">
                <li class="<%=prev_class%>"><a href="./t.jsp?p=<%=p - 1%>">Previous</a></li>
                <li class="next"><a href="./t.jsp?p=<%=p + 1%>" >Next</a></li>
            </ul>
            </div>
         </div>
          <div class="col-xs-6 col-sm-3 sidebar-offcanvas pull-right" id="sidebar" role="navigation">
          <div class="list-group">
            <a href="#" class="list-group-item active">Per Data Source Stats</a>
            <a href="#" id="g" class="list-group-item">GLU</a>
            <a href="#" id="g" class="list-group-item">Experiment</a>
            <a href="#" id="g" class="list-group-item">Bugzilla</a>
            <a href="#" id="g" class="list-group-item">BigIp F5</a>
            <a href="#" id="g" class="list-group-item">Deployments</a>
          </div>
          <%
          	String t = "485,300,400,500,600";
          %>
          	<script type="text/javascript">
				d3.selectAll("#g")
			    	.data([<%=t%>])
			    	.transition()
			    	.duration(2000)
			    	.style("width", function(d) { return  d + "px"; });
			</script>
        </div><!--/span-->
      </div><!--/row-->
       </div>
     </div>
</div>
