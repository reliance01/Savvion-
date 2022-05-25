function editDecimal(id,pms,scale)
{
	var element = document.getElementById(id);
	if (element != null)
	{
		var newurl = '../common/pop_decimal_dataslot.jsp?elementID=' + id + '&pms=' + pms + '&scale=' + scale + '&value=' + element.value;
		MM_openBrWindow(newurl,'editdecimal','scrollbars=yes,resizable=yes,width=690,height=174');
	}
}
