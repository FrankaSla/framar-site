document.addEventListener("DOMContentLoaded", () => {
	const y = document.getElementById("year");
	if (y) y.textContent = new Date().getFullYear();
});

let lbItems = []; // {type:'image'|'video', src:'...', poster?:'...'}
let lbIndex = 0;

function collectGalleryItems() {
  const grid = document.getElementById("galleryGrid");
  const buttons = grid ? Array.from(grid.querySelectorAll(".gallery-item")) : [];
  return buttons.map(btn => ({
    type: btn.getAttribute("data-type") || "image",
    src: btn.getAttribute("data-src"),
    poster: btn.getAttribute("data-poster") || null
  }));
}

function openFromGalleryIndex(index) {
  lbItems = collectGalleryItems();
  lbIndex = index || 0;
  openLightbox();
}

function openLightbox() {
  document.documentElement.classList.add("lb-open");
  const lb = document.getElementById("lightbox");
  lb.classList.add("open");
  lb.setAttribute("aria-hidden", "false");
  document.body.style.overflow = "hidden";
  renderLightbox();
  document.body.classList.add('lb-open');
}

function closeLightbox() {
  document.documentElement.classList.remove("lb-open")
  const lb = document.getElementById("lightbox");
  lb.classList.remove("open");
  lb.setAttribute("aria-hidden", "true");
  document.body.style.overflow = "";
  stopVideoIfAny();
  document.body.classList.remove('lb-open');
}

function stopVideoIfAny() {
  const stage = document.getElementById("lbStage");
  if (!stage) return;
  const vid = stage.querySelector("video");
  if (vid) {
    try { vid.pause(); } catch(e) {}
	//ugasi video skroz da ne ostane "ziv" u pozadini
	vid.removeAttribute("src");
	try { vid.load(); } catch(e)  {}
  }
}

function onBackdropClick(e) {
	if (e.target && e.target.id === "lightbox") closeLightbox();
}

function renderLightbox() {
  const stage = document.getElementById("lbStage");
  const thumbs = document.getElementById("lbThumbs");
  stage.innerHTML = "";
  thumbs.innerHTML = "";

  const item = lbItems[lbIndex];
  if (!item) return;

  // prikaz (slika ili video)
  if (item.type === "video") {
    const v = document.createElement("video");
    v.controls = true;
    v.autoplay = true;
    v.playsInline = true;
    v.src = item.src;
    v.className = "lb-media";
    stage.appendChild(v);
  } else {
    const img = document.createElement("img");
    img.src = item.src;
    img.alt = "Galerija";
    img.className = "lb-media";
    stage.appendChild(img);
  }

  // thumbnail traka
  lbItems.forEach((it, idx) => {
    const btn = document.createElement("button");
    btn.type = "button";
    btn.className = "lb-thumb" + (idx === lbIndex ? " active" : "");
    btn.onclick = () => { lbIndex = idx; renderLightbox(); };

    if (it.type === "video") {
      const d = document.createElement("div");
      d.className = "lb-thumb-video";
      if (it.poster) d.style.backgroundImage = `url('${it.poster}')`;
      d.innerHTML = `<span class="lb-play">▶</span>`;
      btn.appendChild(d);
    } else {
      const t = document.createElement("img");
      t.src = it.src;
      t.alt = "Thumb";
      btn.appendChild(t);
    }

    thumbs.appendChild(btn);
  });

  const active = thumbs.querySelector(".lb-thumb.active");
  if (active) active.scrollIntoView({behavior:"smooth", inline:"center", block:"nearest"});
}

function nextMedia() {
  stopVideoIfAny();
  lbIndex = (lbIndex + 1) % lbItems.length;
  renderLightbox();
}

function prevMedia() {
  stopVideoIfAny();
  lbIndex = (lbIndex - 1 + lbItems.length) % lbItems.length;
  renderLightbox();
}

// tipkovnica: ESC / ← / →
document.addEventListener("keydown", (e) => {
  const lb = document.getElementById("lightbox");
  if (!lb || !lb.classList.contains("open")) return;

  if (e.key === "Escape") closeLightbox();
  if (e.key === "ArrowRight") nextMedia();
  if (e.key === "ArrowLeft") prevMedia();
});

// swipe (mobilno)
let touchStartX = null;
document.addEventListener("touchstart", (e) => {
  const lb = document.getElementById("lightbox");
  if (!lb || !lb.classList.contains("open")) return;
  touchStartX = e.changedTouches[0].clientX;
}, {passive:true});

document.addEventListener("touchend", (e) => {
  const lb = document.getElementById("lightbox");
  if (!lb || !lb.classList.contains("open")) return;
  if (touchStartX === null) return;

  const endX = e.changedTouches[0].clientX;
  const dx = endX - touchStartX;
  touchStartX = null;

  if (Math.abs(dx) < 50) return;
  if (dx < 0) nextMedia();
  else prevMedia();
}, {passive:true});

document.addEventListener("DOMContentLoaded", () => {
	const btn = document.querySelector(".sidebar-toggle");
	if (!btn) return;
	
	//ucitaj stanje
	const saved= localStorage.getItem("framar_sidebar_collapsed");
	if (saved === "1")
	document.body.classList.add("sidebar-collapsed");

		btn.addEventListener("click", (e) => {
			e.preventDefault();
			e.stopPropagation();
			
	document.body.classList.toggle("sidebar-collapsed");
	
		localStorage.setItem("framar_sidebar_collapsed", 
			
	document.body.classList.contains("sidebar-collapsed") ? "1" : "0"
		);
	});
});


document.addEventListener("DOMContentLoaded", () => {
  // godina
  const y = document.getElementById("year");
  if (y) y.textContent = new Date().getFullYear();

  const btn = document.querySelector(".hamburger");
  const backdrop = document.querySelector(".sidebar-backdrop");
  const navLinks = document.querySelectorAll(".sidebar .nav a");

  if (btn) {
    btn.addEventListener("click", () => {
      const isMobile = window.matchMedia("(max-width: 900px)").matches;
      if (isMobile) {
        document.body.classList.toggle("sidebar-open");
      } else {
        document.body.classList.toggle("sidebar-collapsed");
      }
    });
  }

  if (backdrop) {
    backdrop.addEventListener("click", () => {
      document.body.classList.remove("sidebar-open");
    });
  }

  navLinks.forEach(a => {
    a.addEventListener("click", () => {
      // na mobitelu zatvori nakon klika
      if (window.matchMedia("(max-width: 900px)").matches) {
        document.body.classList.remove("sidebar-open");
      }
    });
  });
});

  
document.addEventListener('DOMContentLoaded', () => {
	
	const items = document.querySelectorAll('.reveal');
	if (!items.length) return;
	
	items.forEach(el => el.classList.add("reveal--init"));
	
	const io = new IntersectionObserver((entries) => {
		entries.forEach(e => {
			if (entry.isIntersecting) {
				e.target.classList.add("reveal--visible");
			}
		});
	}, { threshold: 0.15 });
		
	items.forEach(el => io.observe(el));
  });  



