import classes from "./Footer.module.css";

const Footer = () => {
  return (
    <footer className={classes.footer}>
      <p>데이터베이스, 자바프로그래밍 과제</p>
      <p>
        © Copyright 2025{" "}
        <a
          href="https://github.com/jhan117/Cloud-Pet"
          target="_blank"
          rel="noreferrer"
        >
          Kaye
        </a>
        . All Rights Reserved.
      </p>
    </footer>
  );
};

export default Footer;
