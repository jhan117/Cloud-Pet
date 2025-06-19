import Card from "../ui/Card";

import classes from "./Profile.module.css";

const Profile = () => {
  return (
    <Card className={classes.profile}>
      <p className={classes.comment}>누나! 오늘도 절 책임져주세요!</p>
      <img
        className={classes.pic}
        src="/milk.jpg"
        alt="Profile of pet named milk"
      />
      <div className={classes.info}>
        <p>이름: 권밀크</p>
        <p>견종: 푸들</p>
        <p>나이: 9살</p>
        <p>성별: 남</p>
      </div>
    </Card>
  );
};

export default Profile;
