// const BASE_URL = "http://localhost:8080/api";
const BASE_URL = process.env.REACT_APP_API_URL;

const requestAPI = async (url, options, errorMsg) => {
  try {
    const res = await fetch(BASE_URL + url, options);
    if (!res.ok) throw new Error();
    return await res.json().catch(() => null);
  } catch {
    alert(errorMsg);
    return null;
  }
};

// Create
export const createCarePlan = (plan) =>
  requestAPI(
    "/care-plans",
    {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(plan),
    },
    "케어플랜 추가 실패"
  );

// Read
export const getAllCarePlans = () =>
  requestAPI("/care-plans", undefined, "전체 케어플랜 조회 실패");

export const getCarePlansByDate = (date) =>
  requestAPI(
    `/care-plans/filter?date=${date}`,
    { method: "GET" },
    "케어플랜 필터 실패"
  );

// Update
export const updateCarePlan = (plan) =>
  requestAPI(
    `/care-plans/${plan.planId}`,
    {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(plan),
    },
    "케어플랜 수정 실패"
  );

// Delete
export const deleteCarePlan = (planId) =>
  requestAPI(
    `/care-plans/${planId}`,
    { method: "DELETE" },
    "케어플랜 삭제 실패"
  );

// Patch log
export const patchToggleCareLog = (planId, date) =>
  requestAPI(
    `/care-logs/${planId}`,
    {
      method: "PATCH",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(date),
    },
    "완료 상태 변경 실패"
  );
