import Profile from "../components/MainPage/Profile";
import Today from "../components/MainPage/Today";
import Footer from "../layouts/Footer";
import Header from "../layouts/Header";

const MainPage = () => {
  return (
    <>
      <Header />
      <main>
        <Profile />
        <Today />
      </main>
      <Footer />
    </>
  );
};

export default MainPage;
