var serverURL = "http://192.168.2.43:8000";

$(document).delegate('#tregister', 'click', function(e) {
	var email = $("#registerEmail").val();
	var post_data = "name=" + 'JangWeiDong' +
                "&email=" + email +
                "&organization=" + 'ID Company' + 
                "&password=" + '123456' + 
                "&activation_code=" + '6d111f7ca6c5c39c7ec6836bbab0bf1c';

	var url = serverURL + "/signup";
	$.ajax({url: url, type: "POST", data: post_data, success: function(res)
	{
		var data = JSON.parse(res);
		var success = data.success;
		if(success) 
			alert("Register success!");			
		else
			alert(data.reason);			
	}
	})
});

$(document).delegate('#tlogin', 'click', function(e) {
	
	var email = $("#loginEmail").val();		
	var post_data = "email=" + email +
                    "&password=" + '111111';

	var url = serverURL + "/signin";
	$.ajax({url: url, type: "POST", data: post_data, success: function(res)
	{
		var data = JSON.parse(res);
		var success = data.success;
		if(success) 
		{

			alert("Login success!");
		}
		else
			alert("Login Fail!");
	}
	})
});

$(document).delegate('#tnewchannel', 'click', function(e) {		

	var post_data = 'channel_name=' + 'myRTP' + 
					'&protocol=' + 'RTP' + 
					'&destination=' + 'test RTP' + 
					'&jitter_buffers=' + 1000 + 
					'&dynamic_jitter_buffers=' + 3000 + 
					'&format=' + 'MP3' + 
					'&bitrate=' + 100 + 
					'&samplerate=' + 2100 +
					'&stereo=' + true + 
					'&talk_mode=' + false;

	var url = serverURL + "/channel/create";
	$.ajax({url: url, type: "POST", data: post_data, success: function(res)
	{
		var data = JSON.parse(res);
		var success = data.success;
		var channel_name = data.channel_name;
		var created_at = data.created_at;
		if(success) 
		{
			var strMsg = "channel_name = [" + channel_name + "] ======> created_at = [" + created_at + "]";
			alert(strMsg);

		}
		else
			alert("newChannel Fail!");		
	}
	})
});

$(document).delegate('#tupdatechannel', 'click', function(e) {		
	
	var channel_id = $("#updateChannelId").val();
	var post_data = 'channel_id=' + channel_id + 
					'&channel_name=' + 'update SC-RTP' + 
					'&protocol=' + 'SC-RTP' + 
					'&destination=' + 'test SC-RTP' + 
					'&jitter_buffers=' + 1100 + 
					'&dynamic_jitter_buffers=' + 3300 + 
					'&format=' + 'AVI' + 
					'&bitrate=' + 1100 + 
					'&samplerate=' + 22100 +
					'&stereo=' + true + 
					'&talk_mode=' + true;

	var url = serverURL + "/channel/update";
	$.ajax({url: url, type: "POST", data: post_data, success: function(res)
	{
		var data = JSON.parse(res);
		var success = data.success;
		if(success) 
		{
			var channel_name = data.channel_name;
			var updated_at = data.updated_at;
			var strMsg = "channel_name = [" + channel_name + "] ======> updated_at = [" + updated_at + "]";
			alert(strMsg);
		}
		else
			alert("updateChannel Fail!");			
	}
	})
});

$(document).delegate('#tdeletechannel', 'click', function(e) {		
	
	var channel_id = $("#deleteChannelId").val();
	var post_data = 'channel_id=' + channel_id;

	var url = serverURL + "/channel/delete";
	$.ajax({url: url, type: "POST", data: post_data, success: function(res)
	{
		var data = JSON.parse(res);
		var success = data.success;
		if(success) 
		{

			alert("deleteChannel success!");
		}
		else
			alert("deleteChannel Fail!");		
	}
	})
});

$(document).delegate('#tgetchannels', 'click', function(e) {		
	
	var post_data = 'sort_channel_name=' + 'RTP';

	var url = serverURL + "/channel/list";
	$.ajax({url: url, type: "POST", data: post_data, success: function(res)
	{
		var data = JSON.parse(res);
		var success = data.success;
		channles = data.channels;
		if(success) 
		{
			for (var i = 0; i < data.count; i++) {
				alert(channles[i]['channel_name']);
			}			
		}
		else
			alert("getChannels Fail!");		
	}
	})
});