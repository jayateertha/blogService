var selectedBlogId;
var maxBlogsPerPage = 5;
var currPageNum = 0;
var selectedBlogCategory;
var initDone = false;
//var pageContext = "home";

$(document)
		.ready(
				function() {

					console.log("document.ready()");
					console.log("PAGE CONTEXT : " + getPageContext());
					if (getPageContext() == "#homeForm") {
						console.log("INIT +++++++++++++++++++")
						//setPageContext("#homeForm");
						loadHomePage();
						initializeMenu();
						//initDone = true;
					}

					$("#addBlogMenu").click(function(e) {
						console.log("addBlog");
						hideAllUserForms();
						loadForm("newBlogForm");
					})

					$("#newBlogForm").submit(function(e) {
						console.log("newBlogForm");
						hideAllUserForms();
						e.preventDefault();
						createBlog();
					})

					$("#cancelBlogView").click(function(e) {
						console.log("cancelBlogView");
						setSelectedBlogId(undefined);
						loadForm(); // load home page
					})
					$("#cancelBlogEdit").click(function(e) {
						console.log("cancelBlogEdit");
						setSelectedBlogId(undefined);
						loadForm(); // load home page
					})

					$("#cancelBlogAdd").click(function(e) {
						console.log("cancelBlogAdd");
						setSelectedBlogId(undefined);
						loadForm(); // load home page
					})

					$("#editBlogForm").submit(function(e) {
						console.log("editBlogForm");
						console.log("esaveBlog");
						e.preventDefault();
						var blogId = $("#eblogId").val();
						updateBlog(blogId);
					})

					$("#cancelBlogView").click(function(e) {
						console.log("viewBlogForm");
						console.log("cancelBlogView");
						loadForm(); // load home page
					})

					$("#homeMenu").click(function(e) {
						currPageNum = 0;
						console.log("viewBlogForm");
						console.log("cancelBlogView");
						loadForm(); // load home page
					})

					$("#veditBlog").click(function(e) {
						console.log(veditBlog);
						var blog = "#vblogId";
						var blogId = $(blog).val();
						setSelectedBlogId(blogId);
						loadForm("editBlogForm", blogId);
					})

					$("#vdeleteBlog").click(function(e) {
						console.log(vdeleteBlog);
						var blog = "#vblogId";
						var blogId = $(blog).val();
						setSelectedBlogId(blogId);
						deleteBlog(blogId);
					})

					// #AJAX PUT
					function updateBlog(blogId) {
						console.log(window.location.href.match(/^.*\//)[0]);
						console.log(getBaseUrl());
						console.log("updateBlog");
						var signedInUser = getSignedInUser();
						console.log(signedInUser);
						var title = $("#eblogTitle").val();
						var category = $("#eblogCategory").val();
						var blogText = $("#eblogText").val();

						var newBlog = {
							"blogId" : parseInt(blogId),
							"name" : title,
							"category" : category,
							"data" : blogText,
							"user" : {
								"emailId" : signedInUser
							}
						}
						console.log(newBlog);
						var reqUrl = "" + getBaseUrl() + "blogit/blog/";
						console.log(reqUrl);
						$
								.ajax({
									url : reqUrl,
									type : "PUT",
									dataType : "json",
									contentType : "application/json; charset=utf-8",
									success : function(blog, status, xhr) {
										console.log("blog updated: ");
										console.log(blog);
										setSelectedBlogId(blog.blogId);
										loadForm();
									},
									error : function(xhr, status, err) {
										if (!err) {
											setSelectedBlogId(undefined);
											console
													.log("update blog failed : ");
											console.log(err);
											console.log(status);
											loadForm();
										}
									},
									data : JSON.stringify(newBlog),
									headers : {
										"tocken" : getFromBrowserCookie("Authorization"),
										"user" : signedInUser
									}
								})
					}
					
				    // #AJAX DELETE
				    function deleteBlog(blogId) {
				        var reqUrl = "" + getBaseUrl() + "blogit/blog/"
				                + blogId;
				        $.ajax({
				            url : reqUrl,
				            type : "DELETE",
				            success : function(blog, status, xhr) {
				                console.log("Delete blog success: " + blogId);
				                setSelectedBlogId(undefined);
				                loadForm();
				            },
				            error : function(xhr, status, err) {
				                if(!err) {
				                    console.log("Delete blog failed : " + blogId);
				                    console.log(err);
				                    console.log(status);
				                    setSelectedBlogId(undefined);
				                    loadForm();
				                }
				            },
				            headers: {
				                    "tocken": getFromBrowserCookie("Authorization"),
				                    "user":getFromBrowserCookie("userId")
				            }
				        })
				    }

					function loadForm(form, blogId) {
						// var hash = getLocationHash();
						console.log(form);
						blogId = (blogId) ? blogId : getSelectedBlogId();
						console.log("### loadForm");
						console.log(blogId);

						if (form === "newBlogForm") {
							$("#homeForm").hide();
							$("#viewBlogForm").hide();
							//hideAllUserForms();
							$("#newBlogForm").trigger('reset');
							$("#newBlogForm").show();
							setPageContext("#newBlogForm");
							$("#editBlogForm").hide();
							return;
						}

						if (blogId) {
							if (form === "viewBlogForm") {
								$("#viewBlogForm").show()
								readBlog(blogId, function(err, blog) {
									if (!err) {
										console.log("In view blog form");
										$("#homeForm").hide();
										$("#editBlogForm").hide();
										$("#newBlogForm").hide();
										$("#viewBlogForm").trigger('reset');
										//	$("#viewBlogForm").show();
										setPageContext("#viewBlogForm");
										fillViewBlogForm(blog);
									} else {
										console.log("%% @# LOAD HOME PAGE")
										loadHomePage();
									}
								});

							} else if (form === "editBlogForm") {
								$("#editBlogForm").show()
								readBlog(blogId, function(err, blog) {
									if (!err) {
										$("#homeForm").hide();
										$("#viewBlogForm").hide();
										$("#newBlogForm").hide();
										$("#editBlogForm").trigger('reset');
										//$("#editBlogForm").show();
										setPageContext("#editBlogForm");
										fillEditBlogForm(blog);
									} else {
										loadHomePage();
									}
								});
							} else {
								loadHomePage();
							}
						} else {
							loadHomePage();
						}
					}

					function readBlog(blogId, callback) {
						console.log("readBlog");
						var reqUrl = "" + getBaseUrl() + "blogit/blog/"
								+ blogId;
						console.log(reqUrl);
						$.ajax({
							url : reqUrl,
							type : "GET",
							dataType : "json",
							success : function(blog, status, xhr) {
								console.log("Read blog success: ");
								console.log(blog);
								//$("#viewBlogForm").show();
								callback(null, blog);
								//fillViewBlogForm(blog);
								//fillEditBlogForm(blog);
							},
							error : function(xhr, status, err) {
								if (!err && err != "") {
									console.log("Read blog failed : ");
									console.log(err);
									console.log(status);
									setSelectedBlogId(undefined);
									callback("error")
									//loadForm();
								}
							}
						})
					}

					function loadHomePage() {
						console.log("load home form");
						setPageContext("#homeForm");
						var category = getSelectedCategory();
						if (!category || category === "") {
							setSelectedCategory("");
							readBlogs(currPageNum);
						} else {
							readBlogsByCategory(category, false, currPageNum);
						}

						hideAllBlogForms();
						hideAllUserForms();
						//$("#homeForm").trigger('reset');
						$("#homeForm").show();
						//        $("#viewBlogForm").hide();
						//        $("#editBlogForm").hide();
						//        $("#newBlogForm").hide();
					}

					function getSelectedBlogId() {
						return selectedBlogId;
					}

					// #AJAX POST
					function createBlog() {
						console.log("createBlog");
						console.log(window.location.href.match(/^.*\//)[0]);
						console.log(getBaseUrl());
						console.log("createBlog");
						var signedInUser = getSignedInUser();
						console.log(signedInUser);
						var title = $("#nblogTitle").val();
						var category = $("#nblogCategory").val();
						var blogText = $("#nblogText").val();

						var newBlog = {
							//blogId:"",
							"name" : title,
							"category" : category,
							"data" : blogText,
							"user" : {
								"emailId" : signedInUser
							/*            		"name":"",
							 "contactNo":"",
							 "credentials":{
							 "emailId":signedInUser,
							 "password":""
							 }
							 */}
						}
						console.log(newBlog);
						var reqUrl = "" + getBaseUrl() + "blogit/blog/";
						console.log(reqUrl);
						console.log(getFromBrowserCookie("userId"));
						$
								.ajax({
									url : reqUrl,
									type : "POST",
									dataType : "json",
									contentType : "application/json; charset=utf-8",
									success : function(blog, status, xhr) {
										console.log("New blog created: ");
										console.log(blog);
										setSelectedBlogId(blog.blogId);
										loadForm("homeForm");
									},
									error : function(xhr, status, err) {
										if (!err) {
											setSelectedBlogId(undefined);
											console
													.log("Create blog failed : ");
											console.log(err);
											console.log(status);
										}
									},
									data : JSON.stringify(newBlog),
									headers : {
										"tocken" : getFromBrowserCookie("Authorization"),
										"user" : signedInUser
									}
								})
					}

					function readBlogs(pageNum, size) {
						pageNum = (pageNum) ? pageNum : 0;
						size = (size) ? size : maxBlogsPerPage;
						// var reqUrl = "" + getBaseUrl() + "tecblog/blogs?category=WEB";
						var reqUrl = "" + getBaseUrl() + "blogit/blog?page="
								+ pageNum;
						console.log("Read all blogs: ");
						getBlogs(reqUrl);
					}

					function getBlogs(reqUrl) {
						console.log("getBlogs");
						$.ajax({
							url : reqUrl,
							type : "GET",
							dataType : "json",
							success : function(blogs, status, xhr) {
								console.log(reqUrl + " success");
								console.log(blogs);
								fillHomeBlogForm(blogs);
							},
							error : function(xhr, status, err) {
								console.log(reqUrl + " failure");
								console.log(err);
								console.log(status);
								if (currPageNum > 0) {
									currPageNum--;
								} else {
									fillHomeBlogForm([]);
								}
							}
						})
					}

					function fillHomeBlogForm(blogs) {
						console.log("fillHomeBlogForm");
						clearHomeBlogForm();
						var i = 0;
						$("#blogsList li").each(function() {
							console.log("blog item");
							if (blogs && i < blogs.length) {
								var liElement = $(this);
								liElement.html(getBlogHtml(blogs, i));
								console.log(getBlogHtml(blogs, i));
								registerForEVDEvents(i);
							} else {
								return;
							}
							i++;
						});
					}

					function getBlogHtml(blogs, index) {
						console.log("getBlogHtml");
						var blog = blogs[index];
						return "<div><br/><div><input id=\"lblogId"
								+ index
								+ "\" value="
								+ blog.blogId
								+ " style=\"display:none\" readonly>"
								+ "<b id=\"lblogTitle"
								+ index
								+ "\">"
								+ blog.name
								+ " </b></div><button class=\"btn btn-info \" id=\"leditBlog"
								+ index
								+ "\">Edit</button><button "
								+ "id=\"lviewBlog"
								+ index
								+ "\" class=\"btn btn-success \">View</button><button "
								+ " class=\"btn btn-danger \" id=\"ldeleteBlog"
								+ index
								+ "\">Delete</button>"
								+ "</br><input type=\"text\" id=\"lblogCategory"
								+ index
								+ "\" value=\""
								+ blog.category
								+ "\""
								+ " readonly>"
								+ "<br/><input id=\"lblogAuthorId"
								+ index
								+ "\"  value=\""
								+ blog.user.emailId
								+ "\""
								+ " style=\"display:none\" readonly>by <input id=\"lblogAuthName"
								+ index
								+ "\"  value=\""
								+ blog.user.name
								+ " "
								+ "\""
								+ " readonly>"
								+ "<div><textarea id=\"lblogText"
								+ index
								+ "\"  class=\"form-control\" rows=\"5\" readonly required>"
								+ blog.data + "</textarea>"
								+ "</div><br/></div>";
					}

					function showOrHideEditControls(i) {
						console.log("showOrHideEditControls " + i);
						var signedInUser = getSignedInUser();
						console.log(signedInUser);
						var buserId = "#lblogAuthorId" + i;
						var blogOwner = $(buserId).val();
						console.log(blogOwner);

						var editbtn = "#leditBlog" + i;
						var deletebtn = "#ldeleteBlog" + i;
						var viewbtn = "#lviewBlog" + i;
						$(viewbtn).show();
						if (signedInUser == blogOwner) {
							$(editbtn).show();
							$(deletebtn).show();
						} else {
							$(editbtn).hide();
							$(deletebtn).hide();
						}
					}

					function registerForEVDEvents(i) {
						console.log("registerForEVDEvents " + i);
						showOrHideEditControls(i);
						var editbtn = "#leditBlog" + i;
						$(editbtn).click(function(e) {
							console.log(editbtn);
							var blog = "#lblogId" + i;
							var blogId = $(blog).val();
							setSelectedBlogId(blogId);
							setPageContext("#editBlogForm");
							loadForm("editBlogForm", blogId);
						})

						var viewbtn = "#lviewBlog" + i;
						$(viewbtn).click(function(e) {
							console.log(viewbtn);
							var blog = "#lblogId" + i;
							var blogId = $(blog).val();
							setSelectedBlogId(blogId);
							setPageContext("#viewBlogForm");
							loadForm("viewBlogForm", blogId);
						})

						var delbtn = "#ldeleteBlog" + i;
						$(delbtn).click(function(e) {
							console.log(delbtn);
							var blog = "#lblogId" + i;
							var blogId = $(blog).val();
							setSelectedBlogId(blogId);
							deleteBlog(blogId);
						})
					}

					function fillViewBlogForm(blog) {
						console.log("fillViewBlogForm");
						console.log(blog);

						/*						$("#vblogId").val(blog.blogId);
						 $("#vblogTitle").val(blog.name);
						 $("#vblogText").val(blog.data);
						 $("#vblogCategory").val(blog.category);
						 $("#vblogAuthorId").val(blog.user.emailId);
						 $("#vblogAuthName").val(
						 blog.user.name);
						
						 $("#viewBlogForm").show();
						 */

						setTimeout(function() {
							$("#vblogId").val(blog.blogId);
							$("#vblogTitle").val(blog.name);
							$("#vblogText").val(blog.data);
							$("#vblogCategory").val(blog.category);
							$("#vblogAuthorId").val(blog.user.emailId);
							$("#vblogAuthName").val(blog.user.name);
							$("#viewBlogForm").show();
							$("#commentsFirstPage").trigger("click");
						}, 100);
						// setTimeout(function() {
						//     $('#commentsFirstPage').trigger('click');
						// }, 10);
					}

					function fillEditBlogForm(blog) {
						console.log("fillEditBlogForm");
						console.log(blog);

						setTimeout(function() {
							$("#eblogId").val(blog.blogId);
							$("#eblogTitle").val(blog.name);
							$("#eblogText").val(blog.data);
							$("#eblogCategory").val(blog.category);
							$("#eblogAuthorId").val(blog.user.emailId);
							$("#eblogAuthName").val(blog.user.name);
							$("#editBlogForm").show();
						}, 100);
					}

					function setSelectedBlogId(blogId) {
						selectedBlogId = blogId;
					}

					function getSelectedCategory() {
						selectedBlogCategory = $("#searchByCategory").val();
						selectedBlogCategory = (selectedBlogCategory) ? selectedBlogCategory
								: "";
						return selectedBlogCategory;
					}

					function setSelectedCategory(category) {
						$("#searchByCategory").val(category);
						selectedBlogCategory = (category) ? category : "";
					}

					function clearHomeBlogForm() {
						console.log("clearHomeBlogForm");
						var i = 0;
						$("#blogsList li").each(function() {
							var liElement = $(this);
							liElement.html("");
						})
					}

				})