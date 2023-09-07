let token = Cookies.get("Authorization");
document.addEventListener("DOMContentLoaded", function () {
  console.log("신고 js");
  getReportInfo();
});

function getReportInfo() {
  $.ajax({
    type: "GET",
    url: "/mya/reports",
    headers: {'Authorization': token}
  })
  .done(function (response) {
    $('#report_info_table').empty();

    for (let res of response) {

      let temp_html =
          `<tr>
                    <td>${res.id}</td>
                    <td>${res.user}</td>
                    <td>${res.description}</td>
                    <td>${res.reportedUser}</td>
                    <td>${res.reportCategory}</td>
                    <td>${res.createAt}</td>
                    <td>
                      <button onclick="blockUser(${res.reportedUserId})" class="btn btn-outline-primary rounded">영구정지
                      </button>
                      <button onclick="deleteReport()" class="btn btn-outline-primary rounded">삭제
                      </button>
                    </td>
                  </tr>`

      $('#report_info_table').append(temp_html);

    }
  })
  .fail(function (response) {
    alert(response.responseJSON.msg);
  })
}

function blockUser() {

}

function deleteReport() {

}