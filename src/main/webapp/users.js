$(document).ready(function() {
	// loadUserForm();

	$("#signinForm").submit(function(e) {
		console.log("signinForm");
		e.preventDefault();
		authenticateUser();
	});
	$("#cancelSignin").click(function(e) {
		console.log("cancelSignin");
		// $('#signin').load('index.html');
		$(location).attr('href', 'index.html');
	});
	$("#signupForm").submit(function(e) {
		console.log("signupForm");
		e.preventDefault();
		createUser();
	});
	$("#cancelSignup").click(function(e) {
		console.log("cancelSignup");
		// $('#signin').load('index.html');
		$(location).attr('href', 'index.html');
	});
	$("#signupMenu").click(function(e) {
		console.log("signupMenu");
		$("#signupForm").trigger('reset');
		hideAllBlogForms();
		hideAllUserForms();
		$("#signupForm").show();
	});

	$("#signinMenu").click(function(e) {
		console.log("signinMenu");
		$("#signinMenu").trigger('reset');
		hideAllBlogForms();
		hideAllUserForms();
		$("#signinForm").hide();
		$("#signinForm").show();
		// loadUserForm();
	});

	$("#profileMenu").click(function(e) {
		console.log("profileMenu");
		$("#profileMenu").trigger('reset');
		hideAllBlogForms();
		hideAllUserForms();
		loadUserForm("profileForm");
		// $("#profileForm").show();
	});

	$("#adminMenu").click(function(e) {
		console.log("adminMenu");
		$("#adminMenu").trigger('reset');
		// hideAllBlogForms();
		hideAllUserForms();
		readAllUsers();
		$("#adminForm").show();
	});

	$("#profileForm").submit(function(e) {
		console.log("profileForm");
		e.preventDefault();
		updateUser();
	});

	$("#cancelProfile").click(function(e) {
		console.log("cancelProfile");
		$(location).attr('href', 'index.html');
		setPageContext();
	});

	$("#signOffLink").click(function(e) {
		console.log("signOffLink");
		var res = confirm("Really want to Sign off?");
		if (res === true) {
			console.log("sign off");
			var userId = $("#puUserId").val();
			deleteUser(userId);
			clearAuthCookies();
			$(location).attr('href', 'index.html');
		} else {
			console.log("cancel sign off");
		}
	});

	$("#cancelAdmin").click(function(e) {
		console.log("cancelAdmin");
		$(location).attr('href', 'index.html');
	});

	$("#userListAdmin").click(function(e) {
		console.log("userListAdmin");
		readAllUsers();
	});

	$("#signinMenu").click(function(e) {
		console.log("signinMenu");
		$("#signinMenu").trigger('reset');
		// //hideAllBlogForms();
		hideAllUserForms();
		$("#signinForm").show();
		// loadUserForm();
	});

	// #AJAX POST
	function createUser() {
		console.log("createUser");
		console.log(window.location.href.match(/^.*\//)[0]);
		console.log(getBaseUrl());
		console.log("createUser")
		// var userId = $("#suUserId").val();
		var password = $("#suPassword").val();
		var firstName = $("#suFirstName").val();
		var contact = $("#contact").val();
		var emailId = $("#suEmailId").val();
		var user = {
			"name" : firstName,
			"contactNo" : contact,
			"emailId" : emailId,
			"credentials" : {
				"emailId" : emailId,
				"password" : password
			}
		};
		console.log(user);
		var reqUrl = "" + getBaseUrl() + "blogit/user";
		console.log(reqUrl);
		$.ajax({
			url : reqUrl,
			type : "POST",
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			success : function(user, status, xhr) {
				console.log("Sign up success: ");
				console.log(user);
				$("#signupForm").hide();
				$("#signinForm").trigger('reset');
				$("#signinForm").show();
			},
			error : function(xhr, status, err) {
				console.log("Sign up failed : ");
				console.log(err);
				console.log(status);
			},
			data : JSON.stringify(user)
		})
	}

	// #AJAX POST signin
	function authenticateUser() {
		console.log("authenticateUser");
		console.log(window.location.href.match(/^.*\//)[0]);
		console.log(getBaseUrl());
		var emailId = $("#siUserId").val();
		var password = $("#siPassword").val();
		var user = {
			"emailId" : emailId,
			"password" : password
		};
		clearAuthCookies();
		console.log(user);
		var reqUrl = "" + getBaseUrl() + "blogit/user/login";
		console.log(reqUrl);
		$.ajax({
			url : reqUrl,
			type : "POST",
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			success : function(data, status, xhr) {
				// alert("Sign in success: ");
				console.log(data);
				var jwtToken = data['tocken'];
				console.log(jwtToken);
				addToBrowserCookie("Authorization", jwtToken);
				console.log(getFromBrowserCookie());
				addToBrowserCookie("userId", emailId);
				$(location).attr('href', 'index.html');
				setPageContext("#homeForm");
				$("#signinForm").hide();
			},
			error : function(xhr, status, err) {
				alert("Invalid user or password");
				console.log(err);
				console.log(status);
				console.log("authenticateUser failed");
			},
			data : JSON.stringify(user),
			headers : {
				"Authorization" : getFromBrowserCookie("Authorization")
			}
		})
		console.log("authenticate user end");
	}

	function clearAuthCookies() {
		console.log("clearAuthCookies");
		removeBrowserCookie("Authorization");
		removeBrowserCookie("userId");
	}

	$("#signoutMenu").click(function(e) {
		console.log("signoutMenu");
		signOut();
	});

	function signOut() {
		console.log("signOut");

		var reqUrl = "" + getBaseUrl() + "blogit/user/logout";
		console.log(reqUrl);
		var credentials = {
			"emailId" : getFromBrowserCookie("userId"),
			"password" : ""
		};
		$.ajax({
			url : reqUrl,
			type : "DELETE",
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			success : function(data, status, xhr) {
				alert("Successfully logged out.");
				console.log(status);
				$(location).attr('href', 'index.html');
				clearAuthCookies();
			},
			error : function(status, err) {
				alert("Log out failed");
				console.log(err);
				console.log(status);
				console.log("Server error");
			},
			data : JSON.stringify(credentials),
			headers : {
				"tocken" : getFromBrowserCookie("Authorization")
			}
		})
		location.reload(true);
	}

	function readUser(userId) {
		console.log("readUser");
		/*
		 * var user = getFromBrowserCookie("userId"); console.log(user);
		 */
		var reqUrl = "" + getBaseUrl() + "blogit/user/" + userId;
		$.ajax({
			url : reqUrl,
			type : "GET",
			dataType : "json",
			success : function(user, status, xhr) {
				console.log("Read user success: ");
				console.log(user);
				console.log(user.emailId);
				console.log(user["name"]);
				console.log(user["contactNo"]);

				$("#puEmailId").val(user["emailId"]);
				$("#puPassword").val("");
				$("#puFirstName").val(user['name']);
				$("#puContact").val(user.contactNo);
			},
			error : function(xhr, status, err) {
				console.log("Read user failed : ");
				console.log(err);
				console.log(status);
				clearAuthCookies();
				loadUserForm();
			},
			/*
			 * data : user,
			 */headers : {
				"tocken" : getFromBrowserCookie("Authorization")
			}
		})
	}
	
    // #AJAX PUT profile update
    function updateUser() {
        console.log("updateUser");
        console.log(window.location.href.match(/^.*\//)[0]);
        console.log(getBaseUrl());
        console.log("updateUser");
        var emailId = $("#puEmailId").val();
        var password = $("#puPassword").val();
        var name = $("#puFirstName").val();
        var contactNo = $("#puContact").val();
        var user = {
            "emailId" : emailId,
            "name" : name,
            "contactNo" : contactNo,
            "credentials" : {
            	"emailId":emailId,
            	"password":password
            }
        };
        console.log(user);
        var reqUrl = "" + getBaseUrl() + "blogit/user/update";
        console.log(reqUrl);
        $.ajax({
            url : reqUrl,
            type : "PUT",
            dataType : "json",
            contentType : "application/json; charset=utf-8",
            success : function(data, status, xhr) {
            		alert("Profile updated successfully!");
                console.log("Profile update success: ");
                console.log(data);
                //clearAuthCookies();
                $(location).attr('href', 'index.html');
                //loadUserForm();
            },
            error : function(xhr, status, err) {
                console.log("Profile update failed : ");
                console.log(err);
                console.log(status);
                clearAuthCookies();
                loadUserForm();
            },
            data : JSON.stringify(user),
            headers: {
                "tocken": getFromBrowserCookie("Authorization")
            }
        })
    }


	function loadUserForm(form) {
		console.log("loadUserForm");
		var formParam = getQueryStringParams("form");
		var userId = getSignedInUser();
		console.log("### loadUserForm");
		clearPageContext();
		console.log(formParam);
		console.log(userId);
		if (userId) {
			$("#signinForm").hide();
			$("#signupForm").hide();

			if (userId === 'admin' && formParam === 'admin') {
				$("#adminForm").trigger('reset');
				$("#adminForm").show();
				$("#userListTable").hide();
				readAllUsers();
			} else {
				$("#profileForm").trigger('reset');
				$("#profileForm").show();
				readUser(userId);
			}
		} else {
			$("#signinForm").trigger('reset');
			$("#signinForm").show();
			$("#signupForm").hide();
			$("#profileForm").hide();
		}
	}

});
