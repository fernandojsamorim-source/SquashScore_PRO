# SquashScore_PRO
🏆 SquashScore PRO
is a professional, real-time, high-performance system designed specifically for managing and refereeing Squash matches. The ecosystem integrates an Android application (Phone and Wear OS), an interactive high-impact web scoreboard for court tablets/TVs, and a robust backend based on Supabase (PostgreSQL) for collecting granular statistics (Sports Analytics).
🚀 Key Features
- ⏱️ Real-Time Timing and Refereeing: Instant synchronization between the smartwatch (Wear OS), phone, and court web display through a reactive data architecture (DataLayer API and Supabase Realtime).
- 👥 Frequent Players Management: Local database (Room) for storing the regular roster, allowing quick phone contact import (name and phone number) without requiring invasive permissions.
- 📱 SMS Invite Sharing: Automatic batch SMS dispatch for invited opponents, attaching a direct link to the active match room and automatically excluding the organizer (Host).
- 📊 Sports Analytics (Supabase): Granular logging of each scored point (including elapsed time, rally duration, and point-by-point score), paving the way for advanced performance analysis and head-to-head history.
- 📺 LED Board Style Web Scoreboard: Panoramic horizontal web interface optimized for court tablets or large screens, displaying player names, giant digital font scores, set counts, and an interactive footer Leaderboard with group-wide statistics.
🛠️ Technologies Used
- Android (Mobile & Wear OS): Kotlin, Jetpack Compose, Room Database, DataStore, Nearby API, Google PlayServices DataLayer.
- Backend & Cloud: Supabase (PostgreSQL, Realtime Subscriptions, REST APIs).
- Web Scoreboard: HTML5, CSS3 (Google Fonts - Orbitron), Asynchronous JavaScript, Supabase JS Client.
- Communication: Native Android Intents for batch SMS and low-latency synchronization protocols.
📂 Repository Structure
Plaintext

SquashScore_PRO/
├── app/                  # Main Android application (Phone)
├── wear/                 # Wear OS module for the smartwatch
├── shared/               # Shared data models and scoring rules
└── web/                  # Real-time web scoreboard (index.html)
💡 How to Use
1. Initial Setup: Open the mobile app, go to Settings, and register your Frequent Players (defining who the Host is).
2. Start Match: On the setup screen, select the current match players using the quick selector. Upon starting, the watch is triggered and SMS invites are automatically dispatched to opponents.
3. Court Scoreboard: Open the web scoreboard link (index.html?sala=YOUR_PIN) on a tablet or TV at the court to track scores and group standings in real time.
Developed with a focus on high performance, court usability, and analytical precision.
