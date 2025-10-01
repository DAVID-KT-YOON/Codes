import dotenv from "dotenv";
import { fileURLToPath } from "url";
import { dirname, resolve } from "path";
import mysql from "mysql2/promise";
import type { RowDataPacket } from "mysql2";

const __filename = fileURLToPath(import.meta.url);
const __dirname  = dirname(__filename);

// load ../.env ONCE
dotenv.config({ path: resolve(__dirname, "../.env") });

export const pool = mysql.createPool({
  host: process.env.MYSQL_HOST!,
  port: Number(process.env.MYSQL_PORT ?? 3306),
  user: process.env.MYSQL_USER!,
  password: process.env.MYSQL_PASSWORD!,
  database: process.env.MYSQL_DATABASE!,
  waitForConnections: true,
  connectionLimit: 10,
  charset: "utf8mb4",
  timezone: "Z",
});

// Type the rows so TS knows it's an array
type OkRow = RowDataPacket & { ok: number };

try {
  const [rows] = await pool.query<OkRow[]>("SELECT 1 AS ok");
  if (!rows.length || rows[0]!.ok !== 1) throw new Error("DB health-check failed");
  console.log("DB connected ✅");
} catch (e: any) {
  console.error("MySQL error:", { message: e.message, code: e.code, errno: e.errno, sqlState: e.sqlState });
}

