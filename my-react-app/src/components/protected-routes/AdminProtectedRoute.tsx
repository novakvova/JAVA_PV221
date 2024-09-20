import { Navigate } from "react-router-dom";
import user from '../../store/userStore'
import { observer } from "mobx-react";
import { ProtectedRouteProps } from "../../models/Props";

 const AdminProtectedRoute: React.FC<ProtectedRouteProps> = observer(({redirectPath = '/login', children}) => {
   if(user.isAuthorized){
      if(!user.isAdmin){
        redirectPath = '/forbiden'
      }
      else{
        return children;
      } 
    }
    return <Navigate to={redirectPath} replace />
  });
  export default AdminProtectedRoute;