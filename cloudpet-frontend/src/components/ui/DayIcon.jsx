import classes from "./DayIcon.module.css";

const DayIcon = ({ children, selected, onClick }) => {
  return (
    <div
      className={`${classes.con} ${selected && classes.selected}`}
      onClick={onClick}
    >
      {children}
    </div>
  );
};

export default DayIcon;
