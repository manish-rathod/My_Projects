import { model, Schema } from "mongoose";

export interface Posts {
  content: string;
  access: string;
  userId?: string;
  userName?: string;
  _id?: string;
  createdAt?: Date;
  likes?: number;
  likedByMe: boolean;
  imageId?: string;
}

const schema = new Schema<Posts>(
  {
    userId: {
      type: String,
      required: true,
    },
    userName: {
      type: String,
      required: true,
    },
    content: {
      type: String,
      required: true,
    },
    access: {
      type: String,
      required: true,
    },
    likes: {
      type: Number,
      required: true,
    },
    imageId: {
      type: String,
    },
  },
  { timestamps: true }
);

export const Posts_model = model<Posts>("posts", schema);
