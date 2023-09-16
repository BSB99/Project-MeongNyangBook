document.addEventListener("DOMContentLoaded", function () {
  loadPreviousNotifications(); // 이전 알림들 불러오기

  // SSE 연결로 실시간 알림 수신
  let eventSource = new EventSource('/mya/sse/alarm/subscribe');
  eventSource.onmessage = function (event) {
    let alarm = JSON.parse(event.data);
    addNotificationToList(alarm);
  };
});

function loadPreviousNotifications() {
  let token = Cookies.get('Authorization');
  $.ajax({
    url: '/mya/sse/alarm', // 알림 데이터를 가져오는 엔드포인트 URL 설정
    type: "GET",
    contentType: "application/json",
    headers: {
      "Authorization": token,
    },
    success: function (response) {
      console.log(response);
      response.content.forEach(notification => {
        addNotificationToList(notification);
      });
    },
    error: function (jqXHR, textStatus, errorThrown) {
      // 오류 처리
      console.error("AJAX Error:", textStatus, errorThrown);
    }
  });
}

function addNotificationToList(alarm) {
  let notificationList = document.getElementById('notificationList');

  let liElement = document.createElement('li');
  let aElement = document.createElement('a');
  aElement.className = 'dropdown-item';
  aElement.textContent = `${alarm.sender}: ${alarm.body} (${alarm.createdAt})`;
  aElement.onclick = function () {
    deleteNotification(alarm.id, liElement);
  };

  liElement.appendChild(aElement);
  notificationList.prepend(liElement); // 새 알림을 목록의 맨 위에 추가
}

function deleteNotification(alarmId, liElement) {
  let token = Cookies.get('Authorization');
  $.ajax({
    url: `/mya/sse/${alarmId}`,
    type: "DELETE",
    headers: {
      "Authorization": token,
    },
    success: function (response) {
      // 알림 삭제에 성공하면 리스트에서도 해당 항목 삭제
      liElement.remove();
    },
    error: function (jqXHR, textStatus, errorThrown) {
      console.error("AJAX Error:", textStatus, errorThrown);
    }
  });
}