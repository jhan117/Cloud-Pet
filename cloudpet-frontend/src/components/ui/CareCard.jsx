import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCircleCheck as unCheckIcon } from "@fortawesome/free-regular-svg-icons";
import { faCircleCheck as checkIcon } from "@fortawesome/free-solid-svg-icons";
import Card from "./Card";

import classes from "./CareCard.module.css";

const CareCard = ({ plan, isTodo = false, onEdit, completed, onCheck }) => {
  return (
    <Card
      className={`${classes.careCard} ${!isTodo && classes.plans}`}
      onClick={onEdit}
    >
      {isTodo &&
        (completed ? (
          <FontAwesomeIcon icon={checkIcon} onClick={onCheck} />
        ) : (
          <FontAwesomeIcon icon={unCheckIcon} onClick={onCheck} />
        ))}
      <h3 className={`${completed && classes.completed}`}>{plan.planName}</h3>
      {plan && (
        <div>
          <p>
            Every {plan.repeatStrategyDto.intervalValue}{" "}
            {plan.repeatStrategyDto.type}
            {plan.repeatStrategyDto.type === "WEEK" &&
              " on " + plan.repeatStrategyDto.repeatWeek.days.join(", ")}
          </p>
        </div>
      )}
    </Card>
  );
};

export default CareCard;
