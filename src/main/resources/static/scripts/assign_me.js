 /************************* Assign me related functions***********************/

$(document).ready(function(){
     $("#AssignMe").click(function (event) {
          var response =  $('#ticketData').bootstrapTable('getData');
          var username = document.getElementById("username").value;
          alert(username);
          alert(response[0].id);

          var ticketID = response[0].id;
      $.post("/user/assignticket?username="+
document.getElementById("username").value+"&ticketID="+ticketID, function(data, status){
                $("#result").text(data);
        }).fail(function(error) {  $("#result").text("Assignment failed. Please check logs");});




 });

});



