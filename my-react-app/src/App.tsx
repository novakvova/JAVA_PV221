import './App.css'
import CategoryCreatePage from "./components/category/create";
import {SetupInterceptors} from "./interceptor";

function App() {

    SetupInterceptors();

  return (
    <>
        <CategoryCreatePage/>
    </>
  )
}

export default App
