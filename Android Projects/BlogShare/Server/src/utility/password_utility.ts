import crypto from "crypto-js/md5";
export function createHash(password: string): string {
  return crypto(password).toString();
}
