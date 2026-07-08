CREATE DATABASE KiddieLanddaycare_01

USE KiddieLanddaycare_01;

CREATE TABLE Orang_Tua (
	ID_OrangTua VARCHAR(10) PRIMARY KEY NOT NULL,
	Nama_OrangTua  VARCHAR(50)  NOT NULL,
    No_Handphone   VARCHAR(20)  NOT NULL,
    Alamat         VARCHAR(50)  NOT NULL,
    Email          VARCHAR(50)  NOT NULL
);

CREATE TABLE Karyawan (
    ID_Karyawan VARCHAR(10) PRIMARY KEY NOT NULL,
    Nama_Karyawan VARCHAR(50) NOT NULL,
    Username VARCHAR(50) NOT NULL,
    Password VARCHAR(50) NOT NULL,
    Role VARCHAR(20) NOT NULL
);

SELECT TOP 1 * FROM Karyawan;

INSERT INTO Karyawan
VALUES
('K001','Admin Utama','admin','admin123','Admin');

INSERT INTO Karyawan
VALUES
('K002','Caregiver 1','caregiver','care123','Caregiver');

sp_help Karyawan;

CREATE TABLE Paket_Daycare (
    ID_Paket VARCHAR(10) PRIMARY KEY,
    Kategori_Daycare VARCHAR(20) NOT NULL
        CHECK (Kategori_Daycare IN ('Reguler','Active')),
    Kategori_Usia VARCHAR(30) NOT NULL,
    Jenis_Paket VARCHAR(20) NOT NULL
        CHECK (Jenis_Paket IN ('Harian','Mingguan','Bulanan')),
    Hari_Operasional VARCHAR(50) NOT NULL,
    Jam_Masuk TIME NOT NULL,
    Jam_Pulang TIME NOT NULL,
    Harga DECIMAL(18,2) NOT NULL
);

CREATE TABLE Activity_Schedule (
    ID_Activity VARCHAR(10) PRIMARY KEY,
    ID_Paket VARCHAR(10) NOT NULL,
    Hari VARCHAR(15) NOT NULL,
    Jam_Mulai TIME NOT NULL,
    Jam_Selesai TIME NOT NULL,
    Tema_Activity VARCHAR(100) NOT NULL,
    Deskripsi_Activity VARCHAR(255) NOT NULL,
    FOREIGN KEY (ID_Paket)
        REFERENCES Paket_Daycare(ID_Paket)
);

CREATE TABLE Anak (
    ID_Anak VARCHAR(10) PRIMARY KEY,
    ID_OrangTua VARCHAR(10) NOT NULL,
    Nama_Anak VARCHAR(50) NOT NULL,
    Tanggal_Lahir DATE NOT NULL,
    Jenis_Kelamin VARCHAR(1) NOT NULL
        CHECK (Jenis_Kelamin IN ('L','P')),
    Catatan_Khusus VARCHAR(255) NOT NULL,
    FOREIGN KEY (ID_OrangTua)
        REFERENCES Orang_Tua(ID_OrangTua)
);

CREATE TABLE Transaksi_Daycare (
    ID_Daycare VARCHAR(10) PRIMARY KEY,
    ID_Anak VARCHAR(10) NOT NULL,
    ID_Paket VARCHAR(10) NOT NULL,
    ID_Karyawan VARCHAR(10) NOT NULL,
    Tanggal_Penitipan DATE NOT NULL,
    Status_Daycare VARCHAR(20) NOT NULL
        CHECK (Status_Daycare IN ('Aktif','Selesai','Batal')),
    FOREIGN KEY (ID_Anak)
        REFERENCES Anak(ID_Anak),
    FOREIGN KEY (ID_Paket)
        REFERENCES Paket_Daycare(ID_Paket),
    FOREIGN KEY (ID_Karyawan)
        REFERENCES Karyawan(ID_Karyawan)
);

CREATE TABLE Transaksi_Payment (
    ID_Payment VARCHAR(10) PRIMARY KEY,
    ID_OrangTua VARCHAR(10) NOT NULL,
    Tanggal_Payment DATE NOT NULL,
    Total_Bayar DECIMAL(18,2) NOT NULL,
    Metode_Payment VARCHAR(30) NOT NULL,
    Status_Payment VARCHAR(20) NOT NULL
        CHECK (Status_Payment IN ('Lunas','DP')),
    FOREIGN KEY (ID_OrangTua)
        REFERENCES Orang_Tua(ID_OrangTua)
);

CREATE TABLE Transaksi_Daily_Activity (
    ID_Daily_Activity VARCHAR(10) PRIMARY KEY,
    ID_Daycare VARCHAR(10) NOT NULL,
    ID_Activity VARCHAR(10),
    ID_Karyawan VARCHAR(10) NOT NULL,
    Tanggal DATE NOT NULL,
    Jam_CheckIn TIME NOT NULL,
    Jam_CheckOut TIME NOT NULL,
    Aktivitas_Harian VARCHAR(255) NOT NULL,
    Jam_Makan TIME NOT NULL,
    Jam_Tidur TIME NOT NULL,
    Catatan_Caregiver TEXT NOT NULL,
    FOREIGN KEY (ID_Daycare)
        REFERENCES Transaksi_Daycare(ID_Daycare),
    FOREIGN KEY (ID_Activity)
        REFERENCES Activity_Schedule(ID_Activity),
    FOREIGN KEY (ID_Karyawan)
        REFERENCES Karyawan(ID_Karyawan)
);

-- VIEW
CREATE VIEW v_data_anak
AS
SELECT
    a.ID_Anak,
    a.Nama_Anak,
    a.Tanggal_Lahir,
    a.Jenis_Kelamin,

    o.Nama_OrangTua,
    o.No_Handphone
FROM Anak a
INNER JOIN Orang_Tua o
ON a.ID_OrangTua = o.ID_OrangTua;
GO

CREATE VIEW v_laporan_transaksi_daycare
AS
SELECT
    td.ID_Daycare,
    a.Nama_Anak,
    p.Jenis_Paket,
    k.Nama_Karyawan,
    td.Tanggal_Penitipan,
    td.Status_Daycare
FROM Transaksi_Daycare td
INNER JOIN Anak a
ON td.ID_Anak = a.ID_Anak
INNER JOIN Paket_Daycare p
ON td.ID_Paket = p.ID_Paket
INNER JOIN Karyawan k
ON td.ID_Karyawan = k.ID_Karyawan;

CREATE VIEW v_laporan_payment
AS
SELECT
    tp.ID_Payment,
    ot.Nama_OrangTua,
    tp.Tanggal_Payment,
    tp.Total_Bayar,
    tp.Metode_Payment,
    tp.Status_Payment
FROM Transaksi_Payment tp
INNER JOIN Orang_Tua ot
ON tp.ID_OrangTua = ot.ID_OrangTua;

CREATE VIEW v_laporan_aktivitas_harian
AS
SELECT
    da.ID_Daily_Activity,
    an.Nama_Anak,
    ac.Tema_Activity,
    da.Tanggal,
    da.Jam_CheckIn,
    da.Jam_CheckOut,
    da.Aktivitas_Harian,
    da.Catatan_Caregiver
FROM Transaksi_Daily_Activity da
INNER JOIN Transaksi_Daycare td
ON da.ID_Daycare = td.ID_Daycare
INNER JOIN Anak an
ON td.ID_Anak = an.ID_Anak
LEFT JOIN Activity_Schedule ac
ON da.ID_Activity = ac.ID_Activity;
