import { Blog } from "../models/blog_model";
import { Path, POST } from "typescript-rest";

@Path("/blog")
export class BlogController {
  @POST
  async createBlog(body: Blog) {}
}
