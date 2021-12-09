import express from "express";
import { Server } from "typescript-rest";
import services from "./controllers";
require("dotenv").config();

var server: express.Application = express();

export class ApiServer {
  public PORT: String = process.env.PORT || "5000";
  constructor() {
    Server.buildServices(server, ...services);
  }

  start() {
    server.listen(this.PORT, () => {
      console.log("server is running at port " + this.PORT);
      this.printAllRoutes();
    });
  }

  printAllRoutes() {
    console.log("Routes=>");
    Server.getPaths().forEach((element) => {
      console.log(element);
    });
  }
}
