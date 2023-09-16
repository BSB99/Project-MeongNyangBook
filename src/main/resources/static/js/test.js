document.addEventListener("DOMContentLoaded", function () {
  const eventSource = new EventSource("/api/notifications/subscribe");

  // SSE 이벤트 핸들러
  eventSource.onmessage = function (event) {
    console.log(event.data);
    const notification = JSON.parse(event.data);

    // 알림을 notification-list에 추가
    const notificationList = document.getElementById("notification-list");
    if (notificationList) {
      const listItem = document.createElement("li");
      listItem.textContent = notification.content;
      notificationList.appendChild(listItem);
    } else {
      console.error("Element with ID 'notification-list' not found.");
    }
  };
});

// 알림 리스트를 동적으로 생성하고 표시하는 함수
function displayNotifications() {
  // 알림 데이터를 서버에서 가져옵니다.
  $.ajax({
    url: "/api/notifications/", // 알림 데이터를 가져오는 엔드포인트 URL 설정
    type: "GET",
    contentType: "application/json",
    success: function (notifications) {
      // 알림 데이터를 받아와서 표시할 위치를 선택합니다.
      const notificationList = document.getElementById("notification-list");

      // 알림 리스트를 초기화합니다.
      notificationList.innerHTML = "";

      // 알림 데이터를 돌면서 리스트 아이템을 생성합니다.
      notifications.forEach(function (notification) {
        const listItem = document.createElement("li");
        listItem.textContent = notification.content;
        notificationList.appendChild(listItem);
      });
    },
    error: function (jqXHR, textStatus, errorThrown) {
      // 오류 처리
      console.error("AJAX Error:", textStatus, errorThrown);
    }
  });
}

// 페이지 로드 시 알림 리스트를 표시합니다.
$(document).ready(function () {
  displayNotifications();
});

// document.addEventListener("DOMContentLoaded", function() {
//     const eventSource = new EventSource("/api/notifications/subscribe");
//
//     // SSE 이벤트 핸들러
//     eventSource.onmessage = function (event) {
//         const notification = JSON.parse(event.data);
//
//         // 토스트 생성 및 표시
//         const toastContainer = document.getElementById("toast-container");
//         if (toastContainer) {
//             const toast = document.createElement("div");
//             toast.classList.add("toast");
//             toast.setAttribute("role", "alert");
//             toast.setAttribute("aria-live", "assertive");
//             toast.setAttribute("aria-atomic", "true");
//             toast.innerHTML = `
//                 <div class="toast-header">
//                     <strong class="me-auto">${notification.title}</strong>
//                     <small class="text-body-secondary">${notification.time}</small>
//                     <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
//                 </div>
//                 <div class="toast-body">
//                     ${notification.content}
//                 </div>
//             `;
//
//             toastContainer.appendChild(toast);
//
//             // 토스트 표시 및 자동 닫힘
//             const toastInstance = new bootstrap.Toast(toast);
//             toastInstance.show();
//
//             // 토스트가 닫힐 때 제거
//             toast.addEventListener("hidden.bs.toast", function () {
//                 toast.remove();
//             });
//         } else {
//             console.error("Element with ID 'toast-container' not found.");
//         }
//     };
// });

// 알림 전체 삭제 함수
function deleteAllNotifications() {
  $.ajax({
    url: "/api/notifications/delete",
    type: "DELETE",
    success: function (response) {
      // 성공적으로 삭제되었을 때 처리
      if (response.status === 200) {
        const notificationList = document.getElementById("notification-list");
        if (notificationList) {
          notificationList.style.display = "none";
        }
      }
    },
    error: function (jqXHR, textStatus, errorThrown) {
      // 오류 처리
      console.error("AJAX Error:", textStatus, errorThrown);
    }
  });
}

// "알림 전체 삭제" 버튼 클릭 시 삭제 함수 호출
$(document).ready(function () {
  const deleteAllBtn = document.getElementById("delete-all-btn");
  if (deleteAllBtn) {
    deleteAllBtn.addEventListener("click", function () {
      deleteAllNotifications();
    });
  }
});