-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 29 Feb 2020 pada 11.30
-- Versi server: 10.1.30-MariaDB
-- Versi PHP: 7.2.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_inventori`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_barang`
--

CREATE TABLE `tb_barang` (
  `id_barang` varchar(10) NOT NULL,
  `id_kategori` varchar(10) NOT NULL,
  `nama_barang` varchar(50) NOT NULL,
  `harga_beli` double NOT NULL,
  `harga_jual` double NOT NULL,
  `spesifikasi` text NOT NULL,
  `stok` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tb_barang`
--

INSERT INTO `tb_barang` (`id_barang`, `id_kategori`, `nama_barang`, `harga_beli`, `harga_jual`, `spesifikasi`, `stok`) VALUES
('B001', 'K001', 'Flashdisk V Gen 8GB', 45000, 65000, '8GB', 5),
('B010', 'K001', 'Mouse Votre', 10000, 20000, 'Tanpa Wireless', 6),
('B0100', 'K001', 'Flashdisk Gendisk 16GB', 50000, 70000, '16GB', 6),
('B021', 'K001', 'Mouse Logitech', 50000, 65000, 'Tanpa Wireles', 5),
('B035', 'K001', 'Keyboard Votre', 35000, 50000, 'Tanpa Wireless', 5),
('B046', 'K001', 'RAM V Gen 4GB', 275000, 350000, '4GB, DDR3', 5),
('B049', 'K001', 'RAM V Gen 4GB', 350000, 450000, '4GB, DDR4', 3),
('B051', 'K001', 'Harddisk Seagate', 475000, 550000, '500GB', 5),
('B055', 'K001', 'Flashdisk V Gen 16GB', 50000, 75000, '16GB', 6),
('B059', 'K004', 'Laptop Notebook 14', 2000000, 2400000, 'Intel Celerom N3060, 2GB, HDD, 500GB, Intel HD', 2),
('B066', 'K004', 'Laptop Asus A455LD', 3400000, 3700000, 'Intel Core i3 4005, RAM 4GB, HD 500GB, Nvidia GForce 820\n', 2),
('B069', 'K004', 'Laptop Lenovo', 2400000, 2700000, 'Intel Celeron N3350, RAM 4GB,. SSD 128GB', 1);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_brg_keluar`
--

CREATE TABLE `tb_brg_keluar` (
  `no_nota` varchar(10) NOT NULL,
  `id_user` varchar(10) NOT NULL,
  `tgl_keluar` date NOT NULL,
  `hasil_totalhrgjual` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tb_brg_keluar`
--

INSERT INTO `tb_brg_keluar` (`no_nota`, `id_user`, `tgl_keluar`, `hasil_totalhrgjual`) VALUES
('NT001', 'U001', '2020-01-09', '2450000.00'),
('NT002', 'U003', '2020-01-10', '2895000.00'),
('NT003', 'U003', '2020-01-12', '165000.00'),
('NT004', 'U001', '2020-01-14', '2150000.00'),
('NT005', 'U001', '2020-01-17', '680000.00'),
('NT006', 'U005', '2020-01-19', '590000.00'),
('NT007', 'U001', '2020-02-14', '1250000.00'),
('NT008', 'U004', '2020-02-16', '120000.00'),
('nt010', 'U004', '2020-02-15', '2700000.00'),
('NT011', 'U004', '2020-02-29', '220000.00');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_brg_msk`
--

CREATE TABLE `tb_brg_msk` (
  `no_faktur` varchar(10) NOT NULL,
  `id_suplier` varchar(10) NOT NULL,
  `id_user` varchar(10) NOT NULL,
  `tgl_masuk` date NOT NULL,
  `hasil_totalhrgbeli` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tb_brg_msk`
--

INSERT INTO `tb_brg_msk` (`no_faktur`, `id_suplier`, `id_user`, `tgl_masuk`, `hasil_totalhrgbeli`) VALUES
('FK001', 'SP001', 'U005', '2020-01-07', '3390000.00'),
('FK002', 'SP002', 'U004', '2020-01-08', '6675000.00'),
('FK003', 'SP004', 'U003', '2020-01-08', '2690000.00'),
('FK004', 'SP002', 'U004', '2020-01-06', '11600000.00'),
('FK005', 'SP004', 'U004', '2020-01-08', '40000.00'),
('FK006', 'SP002', 'U004', '2020-02-12', '1250000.00'),
('FK007', 'SP011', 'U004', '2020-02-14', '150000.00'),
('FK008', 'SP011', 'U004', '2020-02-19', '2400000.00'),
('FK009', 'SP001', 'U004', '2020-02-25', '350000.00');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_detail_brgkeluar`
--

CREATE TABLE `tb_detail_brgkeluar` (
  `no_urut_keluar` int(10) NOT NULL,
  `no_nota` varchar(10) NOT NULL,
  `id_barang` varchar(10) NOT NULL,
  `harga_jual` decimal(10,2) NOT NULL,
  `harga_beli` decimal(10,2) NOT NULL,
  `jumlah_brgkeluar` int(50) NOT NULL,
  `total_harga_jual` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tb_detail_brgkeluar`
--

INSERT INTO `tb_detail_brgkeluar` (`no_urut_keluar`, `no_nota`, `id_barang`, `harga_jual`, `harga_beli`, `jumlah_brgkeluar`, `total_harga_jual`) VALUES
(1, 'NT001', 'B051', '550000.00', '475000.00', 2, '1100000.00'),
(2, 'NT001', 'B049', '450000.00', '350000.00', 3, '1350000.00'),
(3, 'NT002', 'B001', '65000.00', '45000.00', 1, '130000.00'),
(4, 'NT002', 'B021', '65000.00', '50000.00', 0, '65000.00'),
(5, 'NT002', 'B069', '2700000.00', '2400000.00', 1, '2700000.00'),
(6, 'NT003', 'B021', '65000.00', '50000.00', 1, '65000.00'),
(7, 'NT003', 'B035', '50000.00', '35000.00', 1, '100000.00'),
(8, 'NT004', 'B051', '550000.00', '475000.00', 1, '1100000.00'),
(9, 'NT004', 'B046', '350000.00', '275000.00', 1, '1050000.00'),
(10, 'NT005', 'B001', '65000.00', '45000.00', 1, '130000.00'),
(11, 'NT005', 'B051', '550000.00', '475000.00', 1, '550000.00'),
(12, 'NT006', 'B010', '20000.00', '10000.00', 1, '40000.00'),
(13, 'NT006', 'B051', '550000.00', '475000.00', 1, '550000.00'),
(14, 'NT007', 'B046', '350000.00', '275000.00', 0, '350000.00'),
(15, 'NT007', 'B049', '450000.00', '350000.00', 1, '900000.00'),
(16, 'NT008', 'B0100', '60000.00', '50000.00', 1, '120000.00'),
(17, 'nt010', 'b069', '2700000.00', '2400000.00', 1, '2700000.00'),
(18, 'NT011', 'B0100', '70000.00', '50000.00', 1, '70000.00'),
(19, 'NT011', 'B055', '75000.00', '50000.00', 2, '150000.00');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_detail_brgmsk`
--

CREATE TABLE `tb_detail_brgmsk` (
  `no_urut` int(10) NOT NULL,
  `id_barang` varchar(10) NOT NULL,
  `no_faktur` varchar(10) NOT NULL,
  `harga_beli` decimal(10,2) NOT NULL,
  `jumlah_brgmsk` int(50) NOT NULL,
  `total_harga_beli` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tb_detail_brgmsk`
--

INSERT INTO `tb_detail_brgmsk` (`no_urut`, `id_barang`, `no_faktur`, `harga_beli`, `jumlah_brgmsk`, `total_harga_beli`) VALUES
(1, 'B010', 'FK001', '10000.00', 3, '30000.00'),
(2, 'B021', 'FK001', '50000.00', 6, '300000.00'),
(3, 'B035', 'FK001', '35000.00', 6, '210000.00'),
(4, 'B046', 'FK001', '275000.00', 4, '1100000.00'),
(5, 'B049', 'FK001', '350000.00', 5, '1750000.00'),
(6, 'B051', 'FK002', '475000.00', 5, '2375000.00'),
(7, 'B055', 'FK002', '50000.00', 6, '300000.00'),
(8, 'B059', 'FK002', '2000000.00', 2, '4000000.00'),
(9, 'B001', 'FK003', '45000.00', 7, '315000.00'),
(10, 'B051', 'FK003', '475000.00', 5, '2375000.00'),
(11, 'B066', 'FK004', '3400000.00', 2, '6800000.00'),
(12, 'B069', 'FK004', '2400000.00', 2, '4800000.00'),
(13, 'B010', 'FK005', '10000.00', 4, '40000.00'),
(14, 'B046', 'FK006', '275000.00', 2, '550000.00'),
(15, 'B049', 'FK006', '350000.00', 2, '700000.00'),
(16, 'B0100', 'FK007', '50000.00', 3, '150000.00'),
(17, 'B069', 'FK008', '2400000.00', 1, '2400000.00'),
(18, 'B055', 'FK009', '50000.00', 2, '100000.00'),
(19, 'B0100', 'FK009', '50000.00', 5, '250000.00');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_detail_returjual`
--

CREATE TABLE `tb_detail_returjual` (
  `no_urut_kembali` int(10) NOT NULL,
  `kd_retur` varchar(10) NOT NULL,
  `id_barang` varchar(10) NOT NULL,
  `harga_jual` double NOT NULL,
  `jumlah_brgkembali` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tb_detail_returjual`
--

INSERT INTO `tb_detail_returjual` (`no_urut_kembali`, `kd_retur`, `id_barang`, `harga_jual`, `jumlah_brgkembali`) VALUES
(1, 'RTR01', 'B001', 65000, 1),
(2, 'RTR01', 'B021', 65000, 1),
(3, 'RTR02', 'B035', 50000, 1),
(4, 'RTR03', 'B051', 550000, 1),
(5, 'RTR03', 'B046', 350000, 2),
(6, 'RTR04', 'B001', 65000, 1),
(7, 'RTR05', 'B010', 20000, 1),
(8, 'RTR06', 'B046', 350000, 1),
(9, 'RTR06', 'B049', 450000, 1),
(10, 'RTR07', 'B0100', 60000, 1);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_kategori`
--

CREATE TABLE `tb_kategori` (
  `id_kategori` varchar(10) NOT NULL,
  `nama_kategori` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tb_kategori`
--

INSERT INTO `tb_kategori` (`id_kategori`, `nama_kategori`) VALUES
('K001', 'Hardware'),
('K002', 'Software'),
('K003', 'Speaker'),
('K004', 'Laptop'),
('K005', 'Kamera'),
('K006', 'Pendukung');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_retur_jual`
--

CREATE TABLE `tb_retur_jual` (
  `kd_retur` varchar(10) NOT NULL,
  `no_nota` varchar(10) NOT NULL,
  `tgl_retur` date NOT NULL,
  `keterangan` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tb_retur_jual`
--

INSERT INTO `tb_retur_jual` (`kd_retur`, `no_nota`, `tgl_retur`, `keterangan`) VALUES
('RTR01', 'NT002', '2020-01-13', 'Tidak bisa digunakan'),
('RTR02', 'NT003', '2020-01-18', 'Jelek'),
('RTR03', 'NT004', '2020-01-23', 'Sebagian rusak'),
('RTR04', 'NT005', '2020-01-25', 'Garansi'),
('RTR05', 'NT006', '2020-01-29', 'Tidak berfungsi'),
('RTR06', 'NT007', '2020-02-16', 'Rusak'),
('RTR07', 'NT008', '2020-02-21', 'Tidak bisa dipakai');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_suplier`
--

CREATE TABLE `tb_suplier` (
  `id_suplier` varchar(10) NOT NULL,
  `nama_suplier` varchar(50) NOT NULL,
  `alamat` varchar(50) NOT NULL,
  `no_tlp` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tb_suplier`
--

INSERT INTO `tb_suplier` (`id_suplier`, `nama_suplier`, `alamat`, `no_tlp`) VALUES
('SP001', 'Andalas Komputer', 'Gg. Pandega Marga II, Manggung', '(0274) 5016237'),
('SP002', '76 Computer', 'Jl. Cempaka 9-CC,  Condongcatur, ', '(0274) 885466'),
('SP003', 'Storage Disk SanDisk', 'Jl Kaliurang KM 12', '086491034443'),
('SP004', 'Disass Computer', 'Jl. Sisingamangaraja No.81, Brontokusuman', '(0274) 382406'),
('SP005', 'Logitech Dev Indo', 'Jl Kebayoran No 12 Jakarta', '08471932032'),
('SP006', 'Kenwood Sound System', 'Jl P Sudirman No 55 Surabaya', '081572932'),
('SP007', 'Suparlan', 'Jl Godean Km 5 Samping Toko Barokahy', '082634829'),
('SP008', 'Votre Keyboard Supp', 'Jl  Jend Ahmad Yani No 4 Jakarta', '0876043115'),
('SP009', 'T-Pain Sound', 'Jl Ahmad Yani no 67', '08566012389'),
('SP010', 'Intercooler pad fan X-z', 'Jl Rambutan Gang 20', '089552871'),
('SP011', 'Super Comp', ' Jl kaliurang km 9', '0898490456');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_user`
--

CREATE TABLE `tb_user` (
  `id_user` varchar(10) NOT NULL,
  `nama_user` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `jenis_akses` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tb_user`
--

INSERT INTO `tb_user` (`id_user`, `nama_user`, `password`, `jenis_akses`) VALUES
('U001', 'admin', 'admin', 'Admin'),
('U002', 'useless', 'admin', 'Admin'),
('U003', 'pemilik', 'owner', 'Pemilik'),
('U004', 'Rangga', '12345', 'Pemilik'),
('U005', 'Wisnu', 'admin', 'Admin');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `tb_barang`
--
ALTER TABLE `tb_barang`
  ADD PRIMARY KEY (`id_barang`),
  ADD KEY `id_kategori` (`id_kategori`);

--
-- Indeks untuk tabel `tb_brg_keluar`
--
ALTER TABLE `tb_brg_keluar`
  ADD PRIMARY KEY (`no_nota`),
  ADD KEY `id_user` (`id_user`);

--
-- Indeks untuk tabel `tb_brg_msk`
--
ALTER TABLE `tb_brg_msk`
  ADD PRIMARY KEY (`no_faktur`),
  ADD KEY `id_suplier` (`id_suplier`),
  ADD KEY `id_user` (`id_user`);

--
-- Indeks untuk tabel `tb_detail_brgkeluar`
--
ALTER TABLE `tb_detail_brgkeluar`
  ADD PRIMARY KEY (`no_urut_keluar`),
  ADD KEY `no_nota` (`no_nota`),
  ADD KEY `id_barang` (`id_barang`);

--
-- Indeks untuk tabel `tb_detail_brgmsk`
--
ALTER TABLE `tb_detail_brgmsk`
  ADD PRIMARY KEY (`no_urut`),
  ADD KEY `id_barang` (`id_barang`),
  ADD KEY `no_faktur` (`no_faktur`);

--
-- Indeks untuk tabel `tb_detail_returjual`
--
ALTER TABLE `tb_detail_returjual`
  ADD PRIMARY KEY (`no_urut_kembali`),
  ADD KEY `kd_retur` (`kd_retur`),
  ADD KEY `id_barang` (`id_barang`),
  ADD KEY `kd_retur_2` (`kd_retur`);

--
-- Indeks untuk tabel `tb_kategori`
--
ALTER TABLE `tb_kategori`
  ADD PRIMARY KEY (`id_kategori`);

--
-- Indeks untuk tabel `tb_retur_jual`
--
ALTER TABLE `tb_retur_jual`
  ADD PRIMARY KEY (`kd_retur`),
  ADD KEY `no_nota` (`no_nota`),
  ADD KEY `no_nota_2` (`no_nota`),
  ADD KEY `no_nota_3` (`no_nota`),
  ADD KEY `no_nota_4` (`no_nota`);

--
-- Indeks untuk tabel `tb_suplier`
--
ALTER TABLE `tb_suplier`
  ADD PRIMARY KEY (`id_suplier`);

--
-- Indeks untuk tabel `tb_user`
--
ALTER TABLE `tb_user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `tb_detail_brgkeluar`
--
ALTER TABLE `tb_detail_brgkeluar`
  MODIFY `no_urut_keluar` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT untuk tabel `tb_detail_brgmsk`
--
ALTER TABLE `tb_detail_brgmsk`
  MODIFY `no_urut` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT untuk tabel `tb_detail_returjual`
--
ALTER TABLE `tb_detail_returjual`
  MODIFY `no_urut_kembali` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `tb_barang`
--
ALTER TABLE `tb_barang`
  ADD CONSTRAINT `tb_barang_ibfk_1` FOREIGN KEY (`id_kategori`) REFERENCES `tb_kategori` (`id_kategori`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
