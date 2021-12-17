import { model, Schema } from "mongoose";

export interface User {
  userName: string;
  password: string;
  userId: string;
  _id?: string;
  token?: string;
  tokenExp?: number;
}

const scehma = new Schema(
  {
    userName: {
      type: String,
      required: true,
    },
    password: {
      type: String,
      required: true,
    },
    userId: {
      type: String,
      required: true,
      unique: true,
    },
  },
  { timestamps: true }
);

export const USER_MODEL = model<User>("users", scehma);
