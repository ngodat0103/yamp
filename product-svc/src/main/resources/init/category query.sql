
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
INSERT INTO category(uuid, name, thumbnail_url, slug_name,created_at,last_modified_at,created_by,last_modified_by)
VALUES
(uuid_generate_v4(), 'Laptop', 'https://images.fpt.shop/unsafe/fit-in/60x60/filters:quality(90):fill(transparent)/fptshop.com.vn/Uploads/images/2022/iconcate/icon-laptop.png', 'laptop',now(),now(),'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9','f9ef8aa2-260b-4c32-ae25-7c406e83f0b9'),
(uuid_generate_v4(), 'Điện thoại', 'https://images.fpt.shop/unsafe/fit-in/60x60/filters:quality(90):fill(transparent)/fptshop.com.vn/Uploads/images/2015/ic-dienthoai-desktop.png', 'dien-thoai',now(),now(),'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9','f9ef8aa2-260b-4c32-ae25-7c406e83f0b9'),
(uuid_generate_v4(), 'Máy tính bảng', 'https://images.fpt.shop/unsafe/fit-in/60x60/filters:quality(90):fill(transparent)/fptshop.com.vn/Uploads/images/2015/icon-mtb-desk.png', 'may-tinh-bang',now(),now(),'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9','f9ef8aa2-260b-4c32-ae25-7c406e83f0b9'),
(uuid_generate_v4(), 'Đồng hồ thông minh', 'https://images.fpt.shop/unsafe/fit-in/60x60/filters:quality(90):fill(transparent)/fptshop.com.vn/Uploads/images/2022/iconcate/icon-smartwatch.png', 'dong-ho-thong-minh',now(),now(),'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9','f9ef8aa2-260b-4c32-ae25-7c406e83f0b9'),
(uuid_generate_v4(), 'Tai nghe', 'https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn//content/Tainghe-128x129.png', 'tai-nghe',now(),now(),'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9','f9ef8aa2-260b-4c32-ae25-7c406e83f0b9'),
(uuid_generate_v4(), 'Chuột máy tính', 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/c/h/chuot-khong-day-logitech-mx-master-3-1_2_1.png', 'chuot-may-tinh',now(),now(),'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9','f9ef8aa2-260b-4c32-ae25-7c406e83f0b9'),
(uuid_generate_v4(), 'Màn hình', 'https://images.fpt.shop/unsafe/fit-in/60x60/filters:quality(90):fill(transparent)/fptshop.com.vn/Uploads/images/2022/iconcate/icon-screen.png', 'man-hinh',now(),now(),'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9','f9ef8aa2-260b-4c32-ae25-7c406e83f0b9'),
(uuid_generate_v4(), 'Cáp sạc', 'https://images.fpt.shop/unsafe/fit-in/750x500/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2023/5/30/638210533611674536_LIGHTNING-MFI-BELKIN-PVC-MAUDEN-2.jpg', 'cap-sac',now(),now(),'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9','f9ef8aa2-260b-4c32-ae25-7c406e83f0b9'),
(uuid_generate_v4(), 'Củ sạc', 'https://images.fpt.shop/unsafe/fit-in/214x214/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2020/10/20/637387863045167961_pk-apple-00720432-dd.png', 'cu-sac',now(),now(),'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9','f9ef8aa2-260b-4c32-ae25-7c406e83f0b9'),
(uuid_generate_v4(), 'Loa', 'https://images.fpt.shop/unsafe/fit-in/214x214/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2019/12/26/637129512862634615_00515659.jpg', 'loa',now(),now(),'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9','f9ef8aa2-260b-4c32-ae25-7c406e83f0b9'),
(uuid_generate_v4(), 'Bàn phím', 'https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn//content/ban-phim-128x129.png', 'ban-phim',now(),now(),'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9','f9ef8aa2-260b-4c32-ae25-7c406e83f0b9'),
(uuid_generate_v4(), 'Camera', 'https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn//content/icon-camera-128x129.png', 'camera',now(),now(),'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9','f9ef8aa2-260b-4c32-ae25-7c406e83f0b9');


