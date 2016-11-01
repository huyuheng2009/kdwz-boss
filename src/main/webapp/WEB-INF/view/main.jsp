<%@page pageEncoding="utf8"%>
<%@include file="/tag.jsp"%>
<html>
<head>
<title>this is test </title>
</head>



	
<script type="text/javascript">
$().ready(function() {




var months = ['aaa', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
	$("#month").autocomplete(months, {
		minChars: 0,
		max: 12,
		autoFill: true,
		mustMatch: true,
		matchContains: true,
		scrollHeight: 220,
		formatItem: function(data, i, total) {
			// don't show the current month in the list of values (for whatever reason)
			if ( data[0] == months[new Date().getMonth()] ) 
				return false;
			return data[0];
		}
	});
	

});



</script>
	
</head>

<body>

			<input type="text" id="month" />


</body>

</html>