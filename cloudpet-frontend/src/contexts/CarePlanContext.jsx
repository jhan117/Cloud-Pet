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

  const addCarePlan = async (plan) => {
    await createCarePlan(plan);
    await fetchCarePlans();
  };

  const editCarePlan = async (plan) => {
    await updateCarePlan(plan);
    await fetchCarePlans();
  };

  const removeCarePlan = async (planId) => {
    await deleteCarePlan(planId);
    await fetchCarePlans();
  };

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
