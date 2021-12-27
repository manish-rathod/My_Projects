import { Verify } from "../decorators/security";
import { GET, Path, QueryParam } from "typescript-rest";
import { Image_Model } from "../models/images_model";

@Path("/image")
@Verify()
export class ImageController {
  @GET
  async getPostImage(@QueryParam("imageId") imageId: String) {
    var res = await Image_Model.findById(imageId);
    return res;
  }
}
