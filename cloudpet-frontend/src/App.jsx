import { Navigate, Route, Routes } from "react-router-dom";
import MainPage from "./pages/MainPage";
import PlansPage from "./pages/PlansPage";
import HistoryPage from "./pages/HistoryPage";
import SettingsPage from "./pages/SettingsPage";

function App() {
  return (
    <Routes>
      <Route path="/" element={<MainPage />} />
      <Route path="/plans" element={<PlansPage />} />
      <Route path="/history" element={<HistoryPage />} />
      <Route path="/settings" element={<SettingsPage />} />
      <Route path="*" element={<Navigate ate to="/" replace />} />
    </Routes>
  );
}

export default App;
