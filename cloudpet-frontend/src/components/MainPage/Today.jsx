import { useToday } from "../../contexts/TodayContext";
import Card from "../ui/Card";
import CareCard from "../ui/CareCard";

import classes from "./Today.module.css";

const Today = () => {
  const { todayPlans, loading, toggleComplete } = useToday();

  return (
    <Card className={classes.today}>
      <h2>Today</h2>
      <div className={classes.careCards}>
        {loading ? (
          <p>Loading...</p>
        ) : todayPlans.length > 0 ? (
          todayPlans.map((care) => (
            <CareCard
              key={care.planId}
              plan={care}
              isTodo={true}
              completed={care.isDoneToday}
              onCheck={() => toggleComplete(care.planId)}
            />
          ))
        ) : (
          <p>오늘의 Care가 없습니다.</p>
        )}
      </div>
    </Card>
  );
};

export default Today;
