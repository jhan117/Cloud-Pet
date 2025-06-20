import {
  createContext,
  useContext,
  useState,
  useEffect,
  useCallback,
} from "react";
import {
  getAllCarePlans,
  createCarePlan,
  updateCarePlan,
  deleteCarePlan,
} from "../utils/api";

const CarePlanContext = createContext();
export const useCarePlan = () => useContext(CarePlanContext);

export const CarePlanProvider = ({ children }) => {
  const [carePlans, setCarePlans] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchCarePlans = useCallback(async () => {
    setLoading(true);
    const data = await getAllCarePlans();
    if (data) setCarePlans(data);
    setLoading(false);
  }, []);

  const addCarePlan = useCallback(async (plan) => {
    const data = await createCarePlan(plan);
    if (data) setCarePlans((prev) => [...prev, data]);
  }, []);

  const editCarePlan = useCallback(async (plan) => {
    const data = await updateCarePlan(plan);
    if (data)
      setCarePlans((prev) =>
        prev.map((p) => (p.planId === data.planId ? data : p))
      );
  }, []);

  const removeCarePlan = useCallback(async (planId) => {
    await deleteCarePlan(planId);
    setCarePlans((prev) => prev.filter((p) => p.planId !== planId));
  }, []);

  useEffect(() => {
    fetchCarePlans();
  }, [fetchCarePlans]);

  return (
    <CarePlanContext.Provider
      value={{
        carePlans,
        loading,
        fetchCarePlans,
        addCarePlan,
        editCarePlan,
        removeCarePlan,
      }}
    >
      {children}
    </CarePlanContext.Provider>
  );
};
