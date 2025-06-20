import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { faXmark } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import DayIcon from "./ui/DayIcon";

import classes from "./FormModal.module.css";
import { useCarePlan } from "../contexts/CarePlanContext";
import { useToday } from "../contexts/TodayContext";

const dayList = [
  { label: "S", value: "SUN" },
  { label: "M", value: "MON" },
  { label: "T", value: "TUE" },
  { label: "W", value: "WED" },
  { label: "T", value: "THU" },
  { label: "F", value: "FRI" },
  { label: "S", value: "SAT" },
];

const FormModal = ({ mode, editPlan, showModal, onClose }) => {
  const navigate = useNavigate();
  const { addCarePlan, editCarePlan, removeCarePlan } = useCarePlan();
  const { fetchTodayPlans } = useToday();
  const [planName, setPlanName] = useState(editPlan?.planName || "");
  const [repeatInterval, setRepeatInterval] = useState(
    editPlan?.repeatStrategyDto.intervalValue || 1
  );
  const [type, setType] = useState(editPlan?.repeatStrategyDto.type || "DAY");
  const [daysOfWeek, setDaysOfWeek] = useState(
    editPlan?.repeatStrategyDto?.repeatWeek?.days || ["MON"]
  );

  useEffect(() => {
    document.body.style.overflow = "hidden";
    return () => (document.body.style.overflow = "");
  }, [mode, editPlan]);

  const toggleDay = (day) => {
    setDaysOfWeek((prev) =>
      prev.includes(day) ? prev.filter((d) => d !== day) : [...prev, day]
    );
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (mode === "add") {
      await addCarePlan({
        planName,
        repeatStrategyDto: {
          type,
          intervalValue: repeatInterval,
          repeatWeek:
            type === "WEEK"
              ? {
                  days: daysOfWeek,
                }
              : null,
        },
      });
    } else if (mode === "edit") {
      await editCarePlan({
        ...editPlan,
        planName,
        repeatStrategyDto: {
          ...editPlan?.repeatStrategyDto,
          type,
          intervalValue: repeatInterval,

          repeatWeek:
            type === "WEEK"
              ? {
                  days: daysOfWeek,
                }
              : null,
        },
      });
    }

    await fetchTodayPlans();
    onClose();
    navigate("/plans", { replace: true });
  };

  return (
    <form
      className={`${classes.modalOverlay} ${
        showModal ? classes.slideUp : classes.slideDown
      }`}
      onSubmit={handleSubmit}
    >
      <div className={classes.title}>
        <h1>{mode === "add" ? "새 Care 추가" : "Care 수정"}</h1>
        <FontAwesomeIcon icon={faXmark} onClick={onClose} />
      </div>
      <input
        id="careTitle"
        type="text"
        placeholder="새로운 Care 이름"
        value={planName}
        onChange={(e) => setPlanName(e.target.value)}
        required
        autoFocus
      />
      <div className={classes.strategy}>
        <h2>반복 주기</h2>
        <div className={classes.repeatEvery}>
          <label>Repeat every</label>
          <input
            id="repeatInterval"
            type="number"
            min="1"
            max="9999"
            value={repeatInterval === "" ? "" : repeatInterval}
            onChange={(e) => {
              const val = e.target.value;
              setRepeatInterval(val === "" ? "" : Number(val));
            }}
            required
          />
          <select value={type} onChange={(e) => setType(e.target.value)}>
            <option value="DAY">day</option>
            <option value="WEEK">week</option>
            <option value="MONTH">month</option>
            <option value="YEAR">year</option>
          </select>
        </div>
        {type === "WEEK" && (
          <div className={classes.repeatOn}>
            <p>Repeat on</p>
            <div className={classes.days}>
              {dayList.map((d) => (
                <DayIcon
                  key={d.value}
                  selected={daysOfWeek.includes(d.value)}
                  onClick={() => toggleDay(d.value)}
                >
                  {d.label}
                </DayIcon>
              ))}
            </div>
          </div>
        )}
      </div>
      <div className={classes.buttons}>
        {mode === "edit" && (
          <button onClick={() => removeCarePlan(editPlan.planId)}>
            delete
          </button>
        )}
        <button type="submit">save</button>
      </div>
    </form>
  );
};

export default FormModal;
