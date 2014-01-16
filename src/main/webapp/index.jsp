<%@page import="com.wizecommerce.cts.utils.ChangeRecord"%>
<%@page import="com.wizecommerce.cts.utils.CTSDate"%>
<%@page import="java.util.Iterator"%>

<%@include file="header.jsp" %>

<link rel="stylesheet" type="text/css" href="resources/css/timeline_style.css" />
<script src="resources/js/modernizr.custom.63321.js"></script>
<%
        Integer p = 0;
        String prev_class = "previous";
        if(request.getParameter("p") != null) {
                p =  Integer.parseInt(request.getParameter("p"));
        }
        if(p < 0) {
                prev_class += " disabled";
                p = 0;
        }
%>

        <body>
                <div class="container">
                <section class="main">
                        <ul class="timeline">
        <%
 	            CTSDate ctsDate = new CTSDate();
                Hibernate hibernate = new Hibernate();
                Iterator<?> userSettingIterator = hibernate.executeSelectQuery("FROM UserSettings WHERE uId = 1", true);
            	UserSettings userSetting = (UserSettings) userSettingIterator.next();
            	
                Iterator<?> changeInfoIterator = hibernate.executeSelectQuery("FROM ChangeRecord ORDER BY sourceDatetime DESC", p * userSetting.getRecordsPerPage() , userSetting.getRecordsPerPage());
                hibernate.terminateSession();
                while(changeInfoIterator.hasNext()) {
                        ChangeRecord entry = (ChangeRecord) changeInfoIterator.next();
        %>
	                        <li class="event">
	                                <input type="radio" name="tl-group"/>
	                                <label></label>
	                                <div class="thumb glu"><span><%=entry.getSourceName() %></span></div>
	                                <div class="content-perspective">
	                                        <div class="content">
	                                                <div class="content-inner">
	                                                        <h3><%=ctsDate.epochToString(Integer.parseInt(entry.getSourceDatetimeString()))  %>
	                                                        | <%=entry.getSubSourceName() %>
	                                                        | <%=entry.getDescription() %>
	                                                        | <span class="<%=(entry.getStatus().equalsIgnoreCase("SUCCESS") || entry.getStatus().equalsIgnoreCase("COMPLETED"))?"label label-success":"label label-danger"%>"><%=entry.getStatus() %></span>
	                                                        </h3>
	                                                        <p>
	                                        <%=ctsDate.epochToString(Integer.parseInt(entry.getSourceDatetimeString()))  %>
	                                 </p>
	                                                </div>
	                                        </div>
	                                </div>
	                        </li>
        <%
                }
        %>
                        </ul>
                </section>
			<div class="pull-right">
				<ul class="pager">
                	<li class="<%=prev_class%>"><a href="./?p=<%=p - 1%>">Previous</a></li>
                    <li class="next"><a href="./?p=<%=p + 1%>" >Next</a></li>
                </ul>
            </div>
                </div><!-- /container -->

        </body>