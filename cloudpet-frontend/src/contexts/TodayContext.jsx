import {
  createContext,
  useContext,
  useState,
  useCallback,
  useEffect,
} from "react";
import { getCarePlansByDate, patchToggleCareLog } from "../utils/api";

const TodayContext = createContext();
export const useToday = () => useContext(TodayContext);

export const TodayProvider = ({ children }) => {
  const [todayPlans, setTodayPlans] = useState([]);
  const [loading, setLoading] = useState(false);

  const today = new Date().toISOString().slice(0, 10);

  const fetchTodayPlans = useCallback(async () => {
    setLoading(true);
    const data = await getCarePlansByDate(today);
    setTodayPlans(data || []);
    setLoading(false);
  }, [today]);

  const toggleComplete = async (planId) => {
    const data = await patchToggleCareLog(planId, today);
    if (data)
      setTodayPlans((prev) =>
        prev.map((p) => (p.planId === data.planId ? data : p))
      );
  };

  useEffect(() => {
    fetchTodayPlans();
  }, [fetchTodayPlans]);

  return (
    <TodayContext.Provider
      value={{
        todayPlans,
        loading,
        toggleComplete,
        fetchTodayPlans,
      }}
    >
      {children}
    </TodayContext.Provider>
  );
};
