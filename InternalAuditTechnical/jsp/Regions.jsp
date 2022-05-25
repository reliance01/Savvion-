<%
  String value = request.getParameter("AuditType");
  String retailHUB_Regions = "Select,Delhi,Madhya Pradesh";
  String commercialHUB_Regions = "Select,Delhi,Madhya Pradesh";
  if(value.trim().equalsIgnoreCase("Retail HUB Operation"))
  {
      out.println(retailHUB_Regions);
  }
  else if(value.trim().equalsIgnoreCase("Commercial HUB Operation"))
  {
      out.println(commercialHUB_Regions);
  }
  else
  {
      //out.println("Select");
  }
%>