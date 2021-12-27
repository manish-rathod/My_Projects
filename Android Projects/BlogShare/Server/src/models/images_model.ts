import { model, Schema } from "mongoose";

export interface Images {
  _id?: string;
  imageString: string;
}

const schema = new Schema<Images>({
  imageString: {
    type: String,
    required: true,
  },
});

export const Image_Model = model<Images>("images", schema);
