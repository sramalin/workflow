 /************************* Assign me related functions***********************/


$(document).ready(function(){
     $("#AssignMe").click(function (event) {
          var response =  $('#ticketData').bootstrapTable('getData');
          //var username = [[${LoggedInUser}]];

          var ticketID = response[0].id;
      $.post("/user/assignme?ticketID="+ticketID, function(data, status){
                $("#result").text(data);
        }).fail(function(error) {  $("#result").text("Assignment failed. Please check logs");});




 });

});



